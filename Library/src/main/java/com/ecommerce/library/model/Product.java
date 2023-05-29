package com.ecommerce.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "image"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private int currentQuantity;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image2;
    private boolean is_deleted;
    private boolean is_activated;
    public String CurrencyConverter(double price){
        DecimalFormat decimalFormat = new DecimalFormat("#,###.000");
        String formattedNumber = decimalFormat.format(price).replace(".", ",");
        return formattedNumber;
    }
    public int SalePercentCalculator(double x ,double y){
        double c = x - y;
        return (int) Math.ceil(c/x *100);
    }
    public int SoldPercentage(int x){
        return (int) Math.floor(x/3000.0 *100);
    }
}
