package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.Review;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.ProductRepository;
import com.da.nongsan24.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductDetailController extends CommomController{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CommomDataService commomDataService;
	@Autowired
	ReviewRepository reviewRepository;
	@GetMapping(value = "productDetail")
	public String productDetail(@RequestParam("id") Long id, Model model, User user) {
		Product product = productRepository.findById(id).orElse(null);
		model.addAttribute("product", product);
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
}
