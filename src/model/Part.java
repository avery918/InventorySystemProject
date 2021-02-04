package model;

import java.lang.reflect.Constructor;

public abstract class Part {
    /** This static field attribute will auto generate each part's ID*/
    public static int partNumber =0;

    /** Parts private fields attributes. */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /** Constructor used to set the initial data for either an InHouse or Outsourced object.
        @param id   ID of part.
        @param name Name of part.
        @param price Price of part.
        @param stock In-stock level of part.
        @param min Minimum stock level of part.
        @param max Maximum stock level of part.

     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = ++partNumber;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getters to retrieve data from any of the part's attributes. */
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return stock;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }


    /** Setters used to modify a part's attributes if need be. */
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }



}

