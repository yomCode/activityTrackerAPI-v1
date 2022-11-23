package dev.decagon.Activity_Tracker.repositories;


import dev.decagon.Activity_Tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    User findUserByUsernameAndPassword(String username, String password);


}
