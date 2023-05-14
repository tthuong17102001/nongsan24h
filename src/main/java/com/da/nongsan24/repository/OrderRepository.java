package com.da.nongsan24.repository;

import com.da.nongsan24.entities.Order;
import com.da.nongsan24.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders where user_id = ?1", nativeQuery = true)
    List<Order> findOrderByUserId(Long id);

    List<Order> findByUserAndStatus(User user, int status);
}