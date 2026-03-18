package ru.mescat.message.exception;

public class RemoteServiceException extends RuntimeException {

    private final int status;
    private final String responseBody;

    public RemoteServiceException(int status, String responseBody) {
        super("Remote error: status=" + status + ", body=" + responseBody);
        this.status = status;
        this.responseBody = responseBody;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
