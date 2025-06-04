package repositories;

import java.util.List;

import memento.core.MACRepository;
import models.Follow;

///DOC: FollowRepository class açıklaması ekle
public class FollowRepository extends MACRepository<Follow> {

    public FollowRepository(Class<Follow> modelClass) {
        super(modelClass);
    }


    ///STUB: findByFrom metodunu tamamla
    public List<Follow> findByFrom() {
        return null;
    }

    ///STUB: findByTo metodunu tamamla
    public List<Follow> findByTo() {
        return null;
    }
    
}
