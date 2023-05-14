package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.*;
import com.da.nongsan24.repository.OrderDetailRepository;
import com.da.nongsan24.repository.OrderRepository;
import com.da.nongsan24.repository.ProductRepository;
import com.da.nongsan24.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class ProductDetailController extends CommomController{
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	CommomDataService commomDataService;
	@Autowired
	ReviewRepository reviewRepository;
	@GetMapping(value = "productDetail")
	public String productDetail(@RequestParam("id") Long id, Model model, User user) {
		Product product = productRepository.findById(id).orElse(null);
		List<Order> successfulOrders = orderRepository.findByUserAndStatus(user,2);
		boolean canReview = false;
		for (Order order : successfulOrders) {
			for (OrderDetail orderDetail : order.getOrderDetails()) {
				if (orderDetail.getProduct().getProductId().equals(id)) {
					canReview = true;
					break;
				}
			}
			if (canReview) {
				break;
			}
		}
		model.addAttribute("canReview", canReview);
		model.addAttribute("product", product);
		model.addAttribute("review", new Review());
		List<Review> reviews = reviewRepository.listReviewByProduct(id);
		model.addAttribute("reviewbyid",reviews);
		commomDataService.commomData(model, user);
		listProductByCategory10(model, product.getCategory().getCategoryId());
		return "web/productDetail";
	}
	// Gợi ý top 10 sản phẩm cùng loại
	public void listProductByCategory10(Model model, Long categoryId) {
		List<Product> products = productRepository.listProductByCategory10(categoryId);
		model.addAttribute("productByCategory", products);
	}
	//

	@PostMapping(value = "/addReview")
	public String addReview(@ModelAttribute("review") Review review, @RequestParam(value = "id") Long id, User user) {
		Product product = productRepository.getById(id);
		review.setProduct(product);
		review.setCreated(new Date());
		reviewRepository.save(review);
		return "redirect:/productDetail?id=" + id;
	}
}
