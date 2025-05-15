package repositories;

import java.util.Optional;

import memento.core.MACRepository;
import models.User;

public class UserRepository extends MACRepository<User>{

    public UserRepository() {
        super(User.class);
    }
    
    public Optional<User> findByUsername(String username) {
        return items.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
