package com.da.nongsan24.controller.admin;

import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.OrderDetailRepository;
import com.da.nongsan24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class IndexAdminController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;
	@ModelAttribute(value = "user")
	public User user(Model model, Principal principal, User user) {

		if (principal != null) {
			model.addAttribute("user", new User());
			user = userRepository.findByEmail(principal.getName());
			model.addAttribute("user", user);
		}

		return user;
	}

	@GetMapping(value = "/home")
	public String index(Model model) {
		Object[] countOrder = orderDetailRepository.repoOrderWeek();
		model.addAttribute("countOrder", countOrder);
		Object[] repoCountWeek = orderDetailRepository.repoProductNewWeek();
		model.addAttribute("repoCountWeek", repoCountWeek);
		Object[] repoMoneyWeek = orderDetailRepository.repoMoneyWeek();
		model.addAttribute("repoMoneyWeek", repoMoneyWeek);
		Object[] repoUser = orderDetailRepository.repoUserWeek();
		model.addAttribute("repoUser", repoUser);
		List<Object[]> repoCategory = orderDetailRepository.repoWhereCategory();
		model.addAttribute("repoCategory",repoCategory);
		return "admin/index";
	}
}
