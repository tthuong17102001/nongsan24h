package com.da.nongsan24.controller.admin;

import com.da.nongsan24.entities.Category;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.CategoryRepository;
import com.da.nongsan24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class CategoryController {
	@Value("${upload.path}")
	private String pathUploadImage;
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	UserRepository userRepository;

	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	// show list category - table list
	@ModelAttribute("categories")
	public List<Category> showCategory(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		return categories;
	}

	@GetMapping(value = "/categories")
	public String categories(Model model, Principal principal) {
		Category category = new Category();
		model.addAttribute("category", category);

		return "admin/show-cate";
	}
	@GetMapping(value = "/addCategory")
	public String showAddCate(Model model, Principal principal) {
		Category category = new Category();
		model.addAttribute("category", category);
		return "admin/add-cate";
	}

	// add category
	@PostMapping(value = "/addCategory")
	public String addCategory(@Validated @ModelAttribute("category") Category category, ModelMap model,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "failure");

			return "admin/add-cate";
		}
		categoryRepository.save(category);
		model.addAttribute("message", "successful!");

		return "redirect:/admin/categories";
	}

	// get Edit category
	@GetMapping(value = "/editCategory/{id}")
	public String editCategory(@PathVariable("id") Long id, ModelMap model) {
		Category category = categoryRepository.findById(id).orElse(null);

		model.addAttribute("category", category);

		return "admin/edit-cate";
	}

	// delete category
	@GetMapping("/delete/{id}")
	public String delCategory(@PathVariable("id") Long id, Model model) {
		categoryRepository.deleteById(id);

		model.addAttribute("message", "Delete successful!");

		return "redirect:/admin/categories";
	}
}
