package main.repository;

import main.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAll();

    Optional<Review> findById(Long id);

    void deleteById(Long id);
}
