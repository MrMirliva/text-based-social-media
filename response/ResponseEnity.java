package response;

public class ResponseEnity<T> {
    private T data;
    private boolean isOk;
    private String message;

    public ResponseEnity(T data, boolean isOk, String message) {
        this.data = data;
        this.isOk = isOk;
        this.message = message;
    }
    public ResponseEnity() {
        // Default constructor for serialization/deserialization
    }
    public ResponseEnity(T data) {
        this.data = data;
        this.isOk = true;
        this.message = "Success";
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return isOk;
    }
    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }
    public void setOk() {
        this.isOk = true;
    }
    public boolean isError() {
        return !isOk;
    }
    public void setError() {
        this.isOk = false;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
