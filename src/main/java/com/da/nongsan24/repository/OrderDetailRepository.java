package com.da.nongsan24.repository;

import com.da.nongsan24.entities.Order;
import com.da.nongsan24.entities.OrderDetail;
import com.da.nongsan24.entities.Product;
import com.da.nongsan24.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    @Query(value = "select * from order_details where order_id = ?;", nativeQuery = true)
    List<OrderDetail> findByOrderId(Long id);
    // Statistics by category sold
    @Query(value = "SELECT c.category_name ,\n" +
            "            SUM(o.quantity * o.price) as sum\n" +
            "            FROM order_details o\n" +
            "            INNER JOIN products p ON o.product_id = p.product_id\n" +
            "            INNER JOIN categories c ON p.category_id = c.category_id GROUP BY c.category_name;", nativeQuery = true)
    public List<Object[]> repoWhereCategory();
    // thong ke so luong san pham moi theo tuần
    @Query(value = "SELECT COUNT(*) AS new_product_count\n" +
            "FROM products\n" +
            "WHERE DATE(entered_date) BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW();", nativeQuery = true)
    public Object[] repoProductNewWeek();
    // thong ke doanh thu theo tuần
    @Query(value = "SELECT SUM(odt.quantity * odt.price) AS total_revenue\n" +
            "FROM order_details odt\n" +
            "INNER JOIN orders od ON odt.order_id = od.order_id\n" +
            "WHERE YEARWEEK(od.order_date, 1) = YEARWEEK(CURDATE(), 1)\n" +
            "AND od.order_date <= NOW();", nativeQuery = true)
    public Object[] repoMoneyWeek();
    //thong ke don hàng theo tuan
    @Query(value = "SELECT COUNT(DISTINCT order_id) as order_count\n" +
            "FROM orders\n" +
            "WHERE DATE(order_date) BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW();", nativeQuery = true)
    public Object[] repoOrderWeek();
    //thong ke nguoi dung dang ki trong tuần vừa rồi
    @Query(value = "SELECT COUNT(*) AS user_count\n" +
            "FROM user\n" +
            "WHERE WEEK(register_date) = WEEK(NOW()) \n" +
            "AND YEAR(register_date) = YEAR(NOW());", nativeQuery = true)
    public Object[] repoUserWeek();
}
