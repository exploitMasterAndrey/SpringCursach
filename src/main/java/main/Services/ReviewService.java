package main.Services;

import main.model.Review;
import main.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id){
        return reviewRepository.findById(id);
    }

    public void saveReview(Review review){
        reviewRepository.save(review);
    }

    public void deleteById(Long id){
        reviewRepository.deleteById(id);
    }
}
