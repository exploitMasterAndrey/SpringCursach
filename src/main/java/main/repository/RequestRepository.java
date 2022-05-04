package main.repository;

import main.model.Request;
import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByUser(User user);

    List<Request> findAll();

    Optional<Request> findById(Long id);

}
