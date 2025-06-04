/**
 * Represents a login request containing user credentials.
 * <p>
 * This class extends {@link RootRequest} and is used to encapsulate
 * the username and password required for user authentication.
 * It can also carry cookies for session management.
 * </p>
 *
 * @author Mirliva (Abdullah Gündüz)
 * @since 2025-06-04
 * @version 1.0
 */
package requests;

import java.util.HashMap;

public class LoginRequest extends RootRequest {
    private String username;
    private String password;

    public LoginRequest() {
        super();
    }
    public LoginRequest(String username, String password, HashMap<String, String> cookie) {
        super(cookie);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
