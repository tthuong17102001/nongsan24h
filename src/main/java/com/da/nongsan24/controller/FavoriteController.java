package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.Favorite;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.FavoriteRepository;
import com.da.nongsan24.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class FavoriteController extends CommomController {

	@Autowired
	FavoriteRepository favoriteRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CommomDataService commomDataService;

	@GetMapping(value = "/favorite")
	public String favorite(Model model, User user) {
		List<Favorite> favorites = favoriteRepository.selectAllSaves(user.getUserId());
		commomDataService.commomData(model, user);
		model.addAttribute("favorites", favorites);
		return "web/favorite";
	}

	@GetMapping(value = "/doFavorite")
	public String doFavorite(Model model, Favorite favorite, User user, @RequestParam("id") Long id) {
		Product product = productRepository.findById(id).orElse(null);
		favorite.setProduct(product);
		favorite.setUser(user);
		product.setFavorite(true);
		favoriteRepository.save(favorite);
		commomDataService.commomData(model, user);
		return "redirect:/products";
	}

	@GetMapping(value = "/doUnFavorite")
	public String doUnFavorite(Model model, Product product, User user, @RequestParam("id") Long id) {
		Favorite favorite = favoriteRepository.selectSaves(id, user.getUserId());
		product = productRepository.findById(id).orElse(null);
		product.setFavorite(false);
		favoriteRepository.delete(favorite);
		commomDataService.commomData(model, user);
		return "redirect:/products";
	}
}
