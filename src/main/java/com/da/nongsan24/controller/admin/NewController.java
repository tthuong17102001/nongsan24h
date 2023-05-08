package com.da.nongsan24.controller.admin;

import com.da.nongsan24.entities.Boardnew;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.Review;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.BoardnewRepository;
import com.da.nongsan24.repository.ReviewRepository;
import com.da.nongsan24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class NewController {
    @Value("${upload.path}")
    private String pathUploadImage;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardnewRepository boardnewRepository;
    @ModelAttribute(value = "user")
    public User user(Model model, Principal principal, User user) {

        if (principal != null) {
            model.addAttribute("user", new User());
            user = userRepository.findByEmail(principal.getName());
            model.addAttribute("user", user);
        }

        return user;
    }

    @ModelAttribute("boardnewList")
    public List<Boardnew> showBoardNew(Model model) {
        List<Boardnew> boardnewList = boardnewRepository.findAll();
        model.addAttribute("boardnewList", boardnewList);

        return boardnewList;
    }

    @GetMapping(value = "/news")
    public String news(Model model, Principal principal) {
        Boardnew boardnew = new Boardnew();
        model.addAttribute("boardnew", boardnew);

        return "admin/show-new";
    }

    @GetMapping(value = "/addNew")
    public String showAddNews(Model model, Principal principal) {
        Boardnew boardnew = new Boardnew();
        model.addAttribute("boardnew", boardnew);

        return "admin/add-new";
    }

    // add product
    @PostMapping(value = "/addNew")
    public String addProduct(@ModelAttribute("boardnew") Boardnew boardnew, ModelMap model,
                             @RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {

        try {

            File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {

        }

        boardnew.setImage_link(file.getOriginalFilename());
        Boardnew p = boardnewRepository.save(boardnew);
        if (null != p) {
            model.addAttribute("message", "Update success");
            model.addAttribute("boardnew", boardnew);
        } else {
            model.addAttribute("message", "Update failure");
            model.addAttribute("boardnew", boardnew);
        }
        return "redirect:/admin/news";
    }

    @GetMapping(value = "/editNew/{id}")
    public String editNew(@PathVariable("id") Long id, ModelMap model) {
        Boardnew boardnew = boardnewRepository.findById(id).orElse(null);

        model.addAttribute("boardnew", boardnew);

        return "admin/edit-new";
    }

    @PostMapping("/editNew")
    public String updateNew(@ModelAttribute("boardnew") Boardnew boardnew,@RequestParam("file") MultipartFile file) {
        try {
            File convFile = new File(pathUploadImage + "/" + file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {

        }
        boardnew.setImage_link(file.getOriginalFilename());
        boardnewRepository.save(boardnew);
        return "redirect:/admin/news";
    }

    @GetMapping("/deleteNew/{id}")
    public String delProduct(@PathVariable("id") Long id, Model model) {
        boardnewRepository.deleteById(id);
        model.addAttribute("message", "Delete successful!");

        return "redirect:/admin/news";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}
