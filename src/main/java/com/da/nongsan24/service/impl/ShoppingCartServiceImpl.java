package com.da.nongsan24.service.impl;

import com.da.nongsan24.entities.CartItem;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private Map<Long,CartItem> map = new HashMap<Long, CartItem>();
    @Override
    public int getCount() {
        if(map.isEmpty()){
            return 0;
        }
        return map.values().size();
    }

    @Override
    public double getAmount() {
        return map.values().stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();
    }

    @Override
    public void clear() {
        map.clear();
    }



    @Override
    public Collection<CartItem> getCartItems() {
        return map.values();
    }

    @Override
    public void updateProduct(Long id, int quantity) {
        CartItem cartItem = map.get(id);
        if(cartItem != null){
            if(quantity<=0){
                map.remove(id);
            }else{
                cartItem.setQuantity(quantity);
            }
        }
    }
    @Override
    public void updateQuantity(Collection<CartItem> cartForm) {
        if (cartForm != null) {
            Collection<CartItem> lines = this.getCartItems();
            for (CartItem line : lines) {
                this.updateProduct(line.getProduct().getProductId(), line.getQuantity());
            }
        }
    }
    @Override
    public void remove(Product product) {
        map.remove(product.getProductId());
    }

    @Override
    public void add(Product product, int quantity) {
        CartItem cartItem = map.get(product.getProductId());
        if(cartItem==null){
            cartItem = new CartItem();
            BeanUtils.copyProperties(product, cartItem);
            cartItem.setQuantity(0);
            cartItem.setProduct(product);
            cartItem.setId(product.getProductId());
            map.put(product.getProductId(), cartItem);
        }
        int newQuantity = cartItem.getQuantity() + quantity;
        if(newQuantity <= 0){
            map.remove(product.getProductId());
        }else{
            cartItem.setQuantity(newQuantity);
        }
    }

}
