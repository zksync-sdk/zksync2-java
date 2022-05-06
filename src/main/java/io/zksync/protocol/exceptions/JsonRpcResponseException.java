package io.zksync.protocol.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.web3j.protocol.core.Response;

import java.util.Optional;

public class JsonRpcResponseException extends RuntimeException {

    private final Optional<Response.Error> error;
    private final Optional<Data> data;

    private JsonRpcResponseException(Response<?> response, Data data) {
        super(String.format("%s: %s",
                response.hasError() ? response.getError().getMessage() : "",
                data != null ? data.getMessage() : ""));

        this.error = Optional.ofNullable(response.getError());
        this.data = Optional.ofNullable(data);
    }

    public JsonRpcResponseException(String message) {
        super(message);
        this.error = Optional.empty();
        this.data = Optional.empty();
    }

    public JsonRpcResponseException(String message, Throwable cause) {
        super(message, cause);
        this.error = Optional.empty();
        this.data = Optional.empty();
    }

    public JsonRpcResponseException(Throwable cause) {
        super(cause);
        this.error = Optional.empty();
        this.data = Optional.empty();
    }

    public JsonRpcResponseException(Response<?> response) {
        this(response, response.hasError() ? parseData(response.getError().getData()) : null);
    }

    public Optional<Integer> getCode() {
        return error.map(Response.Error::getCode);
    }

    public Optional<Data> getData() {
        return data;
    }

    private static Data parseData(String data) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return data != null ? objectMapper.readValue(data, Data.class) : null;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static class Data {
        private int code;
        private String message;

        public Data() {
        }

        public Data(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
