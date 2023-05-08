package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CommomDataService commomDataService;
	@GetMapping(value = "/login")
	public String login() {
		return "web/login";
	}
}
