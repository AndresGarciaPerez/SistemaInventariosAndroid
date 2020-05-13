package com.electivaIII.sistemainventario.Models;

public class Accesorio {

    int inventorie_id;
    int quantity;

    int warehouse_id;
    String warehouse;

    int product_id;
    String name;
    String image;
    String product_code;

    public Accesorio () {

    }
    public Accesorio(int inventorie_id, int quantity, int warehouse_id, String warehouse, int product_id, String name, String image, String product_code) {
        this.inventorie_id = inventorie_id;
        this.quantity = quantity;
        this.warehouse_id = warehouse_id;
        this.warehouse = warehouse;
        this.product_id = product_id;
        this.name = name;
        this.image = image;
        this.product_code = product_code;
    }

    public int getInventorie_id() {
        return inventorie_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_code() {
        return product_code;
    }
}
