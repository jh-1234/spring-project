package project.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}
