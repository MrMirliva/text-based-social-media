package repositories;

import java.util.List;

import memento.core.MACRepository;
import models.Follow;

public class FollowRepository extends MACRepository<Follow> {

    public FollowRepository(Class<Follow> modelClass) {
        super(modelClass);
    }


    ///TODO: Implement the methods to interact with the database or data source
    public List<Follow> findByFrom() {
        return null;
    }

    ///TODO: Implement the methods to interact with the database or data source
    public List<Follow> findByTo() {
        return null;
    }
    
}
