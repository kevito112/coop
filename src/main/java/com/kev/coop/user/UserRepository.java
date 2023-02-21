package com.kev.coop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //Data Layer.. provides CRUD operations to be made on the DB
public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("Select u FROM User u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(Long id);
    boolean existsByEmail(String email);
}
