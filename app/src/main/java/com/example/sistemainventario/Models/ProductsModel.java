package com.example.sistemainventario.Models;

import java.util.ArrayList;
import java.util.List;

public class ProductsModel {
    private String productName;
    private String productQuantity;
    private String productCategorie;
    private String productDescripcion;
    private static List products = new ArrayList<>();
    private int images;

    public ProductsModel(){}

    public ProductsModel(String productName, String productQuantity, String productCategorie, String productDescripcion, int images) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productCategorie = productCategorie;
        this.productDescripcion = productDescripcion;
        this.images = images;
    }

    public void setProducts(List products) {
        this.products = products;
    }

    public List getProducts() {
        return products;
    }

    public String getProductName() { return productName; }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getProductCategories() {
        return productCategorie;
    }

    public String getProductDescripcion() {
        return productDescripcion;
    }

    public int getImages() {
        return images;
    }
}
