package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double costPrice;
    private double salePrice;
    private int currentQuantity;
    private String image;
    private String image2;
    private boolean activated;
    private boolean deleted;
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
