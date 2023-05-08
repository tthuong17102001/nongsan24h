package com.da.nongsan24.controller.admin;

import com.da.nongsan24.entities.Order;
import com.da.nongsan24.entities.Review;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.ReviewRepository;
import com.da.nongsan24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ReviewController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @ModelAttribute(value = "user")
    public User user(Model model, Principal principal, User user) {

        if (principal != null) {
            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        return user;
    }

    // list review
    @GetMapping(value = "/reviews")
    public String reviews(Model model, Principal principal) {

        List<Review> reviewList = reviewRepository.findAll();
        model.addAttribute("reviewList", reviewList);

        return "admin/show-review";
    }
    @GetMapping("/deleteReview/{id}")
    public String delProduct(@PathVariable("id") Long id, Model model) {
        reviewRepository.deleteById(id);
        model.addAttribute("message", "Delete successful!");

        return "redirect:/admin/reviews";
    }


}
