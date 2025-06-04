/**
 * Generic response entity class for wrapping API responses.
 *
 * <p>This class standardizes the structure of responses sent from the server,
 * encapsulating the response data, status, and an optional message.
 * It is a revised version of my original Courier class for use in other projects,
 * inspired by Spring’s ResponseEntity class.</p>
 *
 * @param <T> the type of the response data
 *
 * Features:
 * <ul>
 *   <li><b>data</b>: The returned data object</li>
 *   <li><b>isOk</b>: Status indicating whether the operation was successful</li>
 *   <li><b>message</b>: Additional information or error message</li>
 * </ul>
 *
 * @author Mirliva (Abdullah Gündüz)
 * @version 2.0
 * @since 2024-08-01 (original); revised 2025-06-04
 */

package responses;

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
