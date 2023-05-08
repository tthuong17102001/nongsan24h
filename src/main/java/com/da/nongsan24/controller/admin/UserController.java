package com.da.nongsan24.controller.admin;

import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping(value = "/admin/user/list")
	public String user(Model model, Principal principal) {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);
		List<User> users = userRepository.findAll();
		model.addAttribute("users", users);

		return "/admin/user";
	}
}
