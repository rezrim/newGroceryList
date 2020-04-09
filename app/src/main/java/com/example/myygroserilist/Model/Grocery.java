package com.example.myygroserilist.Model;

public class Grocery {
    private String name;
    private String quantity;
    private String dateItemAdd;
    private int id;

    public Grocery() {

    }
    
    public Grocery(String name, String quantity, String dateItemAdd, int id) {
        this.name = name;
        this.quantity = quantity;
        this.dateItemAdd = dateItemAdd;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateItemAdd() {
        return dateItemAdd;
    }

    public void setDateItemAdd(String dateItemAdd) {
        this.dateItemAdd = dateItemAdd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
