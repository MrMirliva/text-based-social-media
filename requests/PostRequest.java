/**
 * Represents a request to create or update a post in the social media application.
 * This class extends {@link RootRequest} and encapsulates the content and optional image URL of a post.
 *
 * <p>
 * Usage example:
 * <pre>
 *     PostRequest request = new PostRequest("Hello world!", "http://example.com/image.jpg");
 * </pre>
 * </p>
 *
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 * @see RootRequest
 */
package requests;

///XXX: BURASI NEDEN BÖYLE YAZILDI?
public class PostRequest extends RootRequest {
    private String content;
    private String imageUrl;

    public PostRequest() {
        super();
    }

    public PostRequest(String content, String imageUrl) {
        super();
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
}
