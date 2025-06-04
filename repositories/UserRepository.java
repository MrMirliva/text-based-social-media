/**
 * UserRepository is a repository class responsible for managing User entities.
 * It extends the generic MACRepository to provide CRUD operations and additional
 * methods specific to User management, such as finding a user by their username.
 * 
 * This class encapsulates the logic for accessing and manipulating User data,
 * ensuring a clean separation between data handling and business logic.
 * 
 * @see memento.core.MACRepository
 * @see models.User
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
package repositories;

import java.util.Optional;

import memento.core.MACRepository;
import models.User;

public class UserRepository extends MACRepository<User>{

    public UserRepository() {
        super(User.class);
    }
    
    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty if not found
     */
    public Optional<User> findByUsername(String username) {
        return getAll().stream()
            .filter(user -> user != null && user.getUsername() != null && user.getUsername().equals(username))
            .findFirst();
    }
}
