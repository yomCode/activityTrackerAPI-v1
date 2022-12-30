package dev.decagon.Activity_Tracker.repositories;


import dev.decagon.Activity_Tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    Optional<User> findUserByUsernameAndPassword(String username, String password);


}
