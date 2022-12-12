package ua.pp.kusochok.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.pp.kusochok.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
