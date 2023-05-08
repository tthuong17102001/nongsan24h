package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController extends CommomController {

    @Autowired
    CommomDataService commomDataService;

    @GetMapping(value = "/contact")
    public String contact(Model model, User user) {

        commomDataService.commomData(model, user);
        return "web/contact";
    }
}

