package com.da.nongsan24.controller;

import com.da.nongsan24.commom.CommomDataService;
import com.da.nongsan24.entities.Boardnew;
import com.da.nongsan24.entities.Favorite;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.User;
import com.da.nongsan24.repository.BoardnewRepository;
import com.da.nongsan24.repository.FavoriteRepository;
import com.da.nongsan24.repository.ProductRepository;
import com.da.nongsan24.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController extends CommomController{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CommomDataService commomDataService;

    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    BoardnewRepository boardnewRepository;
    @Autowired
    ShoppingCartService shoppingCartService;
    @GetMapping(value = "/")
    public String home(Model model, User user) {
        commomDataService.commomData(model, user);
        //rau cu
        List<Product> product_raucu = productRepository.listProductByCategory(1L);
        model.addAttribute("product_raucu",product_raucu);
        //hat
        List<Product> product_hat = productRepository.listProductByCategory(2L);
        model.addAttribute("product_hat",product_hat);
        //rau cu
        List<Product> product_traicay = productRepository.listProductByCategory(3L);
        model.addAttribute("product_traicay",product_traicay);
        //rau cu
        List<Product> product_matong = productRepository.listProductByCategory(4L);
        model.addAttribute("product_matong",product_matong);
        List<Boardnew> boardnewlist = boardnewRepository.findAll();
        model.addAttribute("boardnewlist",boardnewlist);
        bestSaleProduct20(model,user);
        return "web/home";
    }
    // list product ở trang chủ limit 10 sản phẩm mới nhất
    @ModelAttribute("listProduct10")
    public List<Product> listproduct10(Model model) {
        List<Product> productList = productRepository.listProductNew20();
        model.addAttribute("productList", productList);
        return productList;
    }

    // Top 20 best sale.
    public void bestSaleProduct20(Model model, User customer) {
        List<Object[]> productList = productRepository.bestSaleProduct20();
        if (productList != null) {
            ArrayList<Integer> listIdProductArrayList = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                String id = String.valueOf(productList.get(i)[0]);
                listIdProductArrayList.add(Integer.valueOf(id));
            }
            List<Product> listProducts = productRepository.findByInventoryIds(listIdProductArrayList);

            List<Product> listProductNew = new ArrayList<>();

            for (Product product : listProducts) {

                Product productEntity = new Product();

                BeanUtils.copyProperties(product, productEntity);

                Favorite save = favoriteRepository.selectSaves(productEntity.getProductId(), customer.getUserId());

                if (save != null) {
                    productEntity.favorite = true;
                } else {
                    productEntity.favorite = false;
                }
                listProductNew.add(productEntity);

            }

            model.addAttribute("bestSaleProduct20", listProductNew);
        }
    }
}
