package com.da.nongsan24.commom;

import com.da.nongsan24.entities.CartItem;
import com.da.nongsan24.entities.Order;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.FavoriteRepository;
import com.da.nongsan24.repository.ProductRepository;
import com.da.nongsan24.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

@Service
public class CommomDataService {
    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    TemplateEngine templateEngine;
    public void commomData(Model model, User user){
        listCategoryByProductName(model);
        Integer totalSave = 0;
        if(user!=null){
            totalSave = favoriteRepository.selectCountSave(user.getUserId());
        }
        Collection<CartItem> cartItems = shoppingCartService.getCartItems();
        model.addAttribute("cartItems", cartItems);
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            double price = cartItem.getQuantity() * cartItem.getProduct().getPrice();
            totalPrice += price - (price * cartItem.getProduct().getDiscount() / 100);
        }

        model.addAttribute("totalPrice", totalPrice);
        Integer totalCartItems = shoppingCartService.getCount();
        model.addAttribute("totalSave", totalSave);

        model.addAttribute("totalCartItems", totalCartItems);
    }

    public void listCategoryByProductName(Model model){
        List<Object[]> countProductByCategory = productRepository.listCategoryByProductName();
        model.addAttribute("countProductByCategory",countProductByCategory);
    }
    public void sendSimpleEmail(String email, String subject, String contentEmail, Collection<CartItem>cartItems, double totalPrice, Order orderFinal)throws MessagingException{
        Locale locale = LocaleContextHolder.getLocale();
        Context context = new Context(locale);
        context.setVariable("cartItems",cartItems);
        context.setVariable("totalPrice",totalPrice);
        context.setVariable("orderFinal",orderFinal);
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,"UTF-8");
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setTo(email);
        String htmlContent = "";
        htmlContent = templateEngine.process("mail/email_en.html",context);
        mimeMessageHelper.setText(htmlContent,true);
        emailSender.send(message);
    }
}
