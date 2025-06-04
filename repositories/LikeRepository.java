package repositories;

import memento.core.MACRepository;
import models.Like;
///DOC:LikeRepository class açıklaması ekle
public class LikeRepository extends MACRepository<Like> {

    public LikeRepository(Class<Like> modelClass) {
        super(modelClass);
    }

    ///TO_VERIFY: exists methodunu kontrol et.
    public boolean exists(int postId, int userId) {
        return getAll().stream()
                .anyMatch(like -> like != null && like.getPostId() == postId && like.getUserId() == userId);
    }

    ///STUB: countByPostId metodunu tamamla
    public int countByPostId(int postId) {
        return 0;
    }

    ///STUB: deleteByUserIdAndPostId metodunu tamamla
    public boolean deleteByUserIdAndPostId(int userId, int postId) {
        return true;
    }
    
}
