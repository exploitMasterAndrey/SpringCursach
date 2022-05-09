package main.controller;

import main.Services.RequestService;
import main.Services.ReviewService;
import main.Services.UserService;
import main.model.*;
import main.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    private Authentication authentication;

    @GetMapping("/requestform")
    public String getRequestFormPage(Model model){
        Request request = new Request();
        model.addAttribute("request", request);
        return "requestform";
    }


    @GetMapping("/home")
    public String getHomePage(){
        return "home";
    }

    @PostMapping("/requestform")
    public String addRequest(@ModelAttribute("request")Request request){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        request.setStatus("На рассмотрении");
        request.setUser(userService.findByEmail(email).get());
        requestService.saveRequest(request);
        return "redirect:/requests";
    }

    @GetMapping("/reviews")
    public String getReviewsPage(Model model){
        List<Review> reviewList = reviewService.getAllReviews();
        List<ReviewPair> pairs = new ArrayList<>();

        User temp = new User();

        for (Review review : reviewList){
            temp = review.getUser();
            pairs.add(new ReviewPair(temp, review));
        }

        model.addAttribute("pairs", pairs);

        return "reviews";
    }

    @PostMapping("/reviews")
    public String deleteReview(@ModelAttribute("review") Review review){
        reviewService.deleteById(review.getId());
        return "redirect:/reviews";
    }

    @GetMapping("/reviewform")
    public String getReviewFormPage(Model model){
        Review review = new Review();
        model.addAttribute("review", review);

        return "reviewform";
    }

    @PostMapping("/reviewform")
    public String createNewReview(@ModelAttribute("review")Review review){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        review.setUser(userService.findByEmail(email).get());

        reviewService.saveReview(review);

        return "redirect:/reviews";
    }

    @GetMapping("/requests")
    public String getRequestsPage(Model model){
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<Request> requestList = requestService.getAllRequestsByUser(userService.findByEmail(email).get());

        model.addAttribute("requestList", requestList);
        return "requests";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<Request> requestList = requestService.getAllRequests();
        List<Pair> pairs = new ArrayList<>();

        User temp = new User();

        for(Request request : requestList){
            temp = request.getUser();
            pairs.add(new Pair(temp, request));
        }
        model.addAttribute("pairs", pairs);


        Request changeStatus = new Request();
        model.addAttribute("changeStatus", changeStatus);
        return "admin";
    }

    @PostMapping("/admin")
    public String changeStatus(@ModelAttribute("changeStatus")Request changeStatus){
        Request request = requestService.findById(changeStatus.getId());
        request.setStatus(changeStatus.getStatus());
        requestService.saveRequest(request);
        return "redirect:/admin";
    }
}
