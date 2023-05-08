package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.Boardnew;
import com.da.nongsan24.repository.BoardnewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BoardnewController extends CommomController{
    @Autowired
    CommomDataService commomDataService;
    @Autowired
    BoardnewRepository boardnewRepository;
    @GetMapping(value = "/news-list")
    public String showNewList(Model model){
        List<Boardnew> boardnewList = boardnewRepository.findAll();
        model.addAttribute("boardnewlist",boardnewList);
        return "web/blog-archive";
    }
    @GetMapping(value = "/new-list-detail")
    public String showNewListDetail(Model model, @RequestParam(value = "id")Long id){
        Boardnew boardnew = boardnewRepository.findById(id).orElse(null);
        model.addAttribute("boardnew",boardnew);
        List<Boardnew> boardnewList = boardnewRepository.findAll();
        model.addAttribute("boardnewlist",boardnewList);
        return "web/blog-single";
    }
}
