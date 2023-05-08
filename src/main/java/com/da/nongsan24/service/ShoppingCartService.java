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
    void remove(Product product);
    Collection<CartItem> getCartItems();

    void remove(CartItem item);

    void add(CartItem item);
}
