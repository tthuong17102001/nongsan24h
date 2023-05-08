package com.da.nongsan24.service;

import com.da.nongsan24.entities.Order;
import com.da.nongsan24.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderDetailService {

    @Autowired
    OrderRepository repo;

    public List<Order> listAll() {
        return (List<Order>) repo.findAll();
    }

}
