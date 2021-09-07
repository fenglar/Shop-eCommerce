package com.shop.common.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length =256, unique=true)
    private String name;
    @Column(nullable = false, length =256, unique=true)
    private String alias;
    @Column(nullable = false, length =512, name="short_description")
    private String shortDescription;
    @Column(nullable = false, length =4096, name="full_description")
    private String fullDescription;

    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "updated_time")
    private Date updatedTime;


    private boolean enabled;
    @Column(name = "in_stock")
    private boolean inStock;

    private float cost;

    private float price;
    @Column(name = "discount_percent")
    private float discountPercent;

    private float length;
    private float width;
    private float height;
    private float weight;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    public Product(Integer id, String name, String alias, String shortDescription, String fullDescription,
                   Date createdTime, Date updatedTime, boolean enabled, boolean inStock,
                   float cost, float price, float discountPercent, float length, float width, float height, float weight,
                   Category category, Brand brand) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.enabled = enabled;
        this.inStock = inStock;
        this.cost = cost;
        this.price = price;
        this.discountPercent = discountPercent;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.category = category;
        this.brand = brand;
    }
}
