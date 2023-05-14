package com.da.nongsan24.repository;

import com.da.nongsan24.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    //List product by category
    @Query(value = "select * from products where category_id = ?",nativeQuery = true)
    public List<Product> listProductByCategory(Long categoryId);
    // Top 10 product by category
    @Query(value = "SELECT * FROM products AS b WHERE b.category_id = ?;", nativeQuery = true)
    List<Product> listProductByCategory10(Long categoryId);
    // List product(20) new
    @Query(value = "select * from products order by entered_date desc limit 20;",nativeQuery = true)
    public List<Product> listProductNew20();
    //Search Product
    @Query(value = "SELECT * FROM products WHERE product_name LIKE %?1%" , nativeQuery = true)
    public List<Product> searchProduct(String productName);
    // Count quantity by product
    @Query(value = "SELECT c.category_id,c.category_name,category_image,\r\n"
            + "COUNT(*) AS SoLuong\r\n"
            + "FROM products p\r\n"
            + "JOIN categories c ON p.category_id = c.category_id\r\n"
            + "GROUP BY c.category_id;" , nativeQuery = true)
    List<Object[]> listCategoryByProductName();
    // Top 20 product best sale
    @Query(value = "SELECT p.product_id,\r\n"
            + "COUNT(*) AS SoLuong\r\n"
            + "FROM order_details p\r\n"
            + "JOIN products c ON p.product_id = c.product_id\r\n"
            + "GROUP BY p.product_id\r\n"
            + "ORDER by SoLuong DESC limit 20;", nativeQuery = true)
    public List<Object[]> bestSaleProduct20();
//san pham noi bat
    @Query(value = "SELECT p.product_id, COUNT(*) AS SoLuongDanhGia " +
            "FROM order_details o " +
            "JOIN products p ON o.product_id = p.product_id " +
            "JOIN review r ON r.product_id = p.product_id " +
            "GROUP BY p.product_id " +
            "ORDER BY SoLuongDanhGia DESC " +
            "LIMIT 20;", nativeQuery = true)
    public List<Object[]> bestRatedProducts20();
    // Hàng còn
    @Query(value = "select * from products o where product_id in :ids", nativeQuery = true)
    List<Product> findByInventoryIds(@Param("ids") List<Integer> listProductId);
}
