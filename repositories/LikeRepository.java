package repositories;

import memento.core.MACRepository;
import models.Like;

public class LikeRepository extends MACRepository<Like> {

    public LikeRepository(Class<Like> modelClass) {
        super(modelClass);
    }

    public boolean exists(int postId, int userId) {
        return true; // TODO: Implement the logic to check if a like exists for the given postId and userId
    }

    public int countByPostId(int postId) {
        return 0; // TODO: Implement the logic to count likes for the given postId
    }

    public boolean deleteByUserIdAndPostId(int userId, int postId) {
        return true; // TODO: Implement the logic to delete a like by userId and postId
    }
    
}
