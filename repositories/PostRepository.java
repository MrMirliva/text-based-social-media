package repositories;

import java.util.List;

import memento.core.MACRepository;
import models.Post;

public class PostRepository extends MACRepository<Post> {

    public PostRepository(Class<Post> modelClass) {
        super(modelClass);
    }

    ///TODO: Implement the methods to interact with the database or data source
    public List<Post> findByUserId(int userId) {
        return null;
    }
    
}
