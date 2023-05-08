package com.da.nongsan24.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private int quantity;
    private double price;
    private int discount;
    private String productImage;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date enteredDate;
    private Boolean status;
    public boolean favorite;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

}
