/**
 * RootRequest is an abstract base class representing a generic request that contains cookie information.
 * It provides methods to get and set cookies, which are stored as key-value pairs in a HashMap.
 * Subclasses should extend this class to implement specific types of requests.
 *
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
package requests;

import java.util.HashMap;

public abstract class RootRequest {
    private HashMap<String, String> cookie;

    public RootRequest() {
        this.cookie = new HashMap<>();
    }
    public RootRequest(HashMap<String, String> cookie) {
        this.cookie = cookie;
    }

    public HashMap<String, String> getCookie() {
        return cookie;
    }
    public void setCookie(HashMap<String, String> cookie) {
        this.cookie = cookie;
    }
}
