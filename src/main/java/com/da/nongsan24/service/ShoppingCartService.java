package com.da.nongsan24.service;

import com.da.nongsan24.entities.CartItem;
import com.da.nongsan24.entities.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ShoppingCartService {
    int getCount();
    double getAmount();
    void clear();
    Collection<CartItem> getCartItems();
    void updateProduct(Long id, int quantity);


    void remove(Product product);

    void add(Product product, int quantity);
    void updateQuantity(Collection<CartItem> cartForm);
}
