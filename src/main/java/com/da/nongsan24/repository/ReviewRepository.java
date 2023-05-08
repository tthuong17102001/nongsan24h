package com.da.nongsan24.repository;

import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    //list review by product_id
    @Query(value = "select * from review where product_id = ?",nativeQuery = true)
    public List<Review> listReviewByProduct(Long productId);
}
