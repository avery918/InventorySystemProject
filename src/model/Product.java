package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    /** This static field attribute will auto generate each product's ID*/
    public static int productNumber = 0;

    /** Products private fields attributes.
     @param id   ID of product.
     @param name Name of product.
     @param price Price of product.
     @param stock In-stock level of product.
     @param min Minimum stock level of product.
     @param max Maximum stock level of product.*/
    private int id;
    private String name;
    private double price;
    private int stock = 0;
    private int min;
    private int max;

    /** This ObservableList will hold parts associated that particular product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Products constructor used to set the initial data for a new product. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        /** Product's ID number. */
        this.id = ++productNumber;

        /** Product's name. */
        this.name = name;

        /** Product's price. */
        this.price = price;

        /** Product's in-stock level. */
        this.stock = stock;

        /** Product's minimum stock level. */
        this.min = min;

        /** Product's maximum stock level. */
        this.max = max;
    }

    /** Getter for product's id.
     * @return Returns product's ID number. */
    public int getId() {
        return id;
    }

    /** Getter for product's name.
     * @return Returns product's name. */
    public String getName() {
        return name;
    }

    /** Getter for product's price.
     * @return Returns product's price. */
    public double getPrice() {
        return price;
    }

    /** Getter for product's stock.
     * @return Returns product's in-stock level. */
    public int getStock() {
        return stock;
    }

    /** Getter for product's min stock amount.
     * @return Returns product's minimum allowed stock amount. */
    public int getMin() {
        return min;
    }

    /** Getter for product's max stock amount.
     * @return Returns product's  maximum allowed stock amount. */
    public int getMax() {
        return max;
    }

    /** Setter for product's id.
     * @param id The product's auto generate ID number. */
    public void setId(int id) {
        this.id = id;
    }
    /** Setter for product's name.
        @param name Name of the product */
    public void setName(String name) {
       this.name = name;
    }

    /** Setter for product's price.
     * @param price The price of the product. */
    public void setPrice(double price) {
        this.price = price;
    }
    /** Setter for product's in-stock level.
     * @param stock In-stock level of product. */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /** Setter for product's minimum stock level.
     * @param min Minimum amount of product stock. */
    public void setMin(int min) {
        this.min = min;
    }
    /** Setter for product's maximum stock level.
     * @param max Max amount of product stock. */
    public void setMax(int max) {
        this.max = max;
    }

    /** This Method adds a part to the associatedParts ObservableList.
     @param part Part to be added. */
    public void addAssociatedPart(Part part){

        associatedParts.add(part);
    }

    /** This Method retrieves a Product's associated parts.*/
    public ObservableList<Part>  getAllAssociatedParts(){
        return associatedParts;
    }

    /** This Method true or false depending if part was deleted or not.
     @param selectedAssociatedPart Part to be removed. */
    public boolean deleteAssociatedParts(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /** This method checks each field to make sure they fall within the logical parameters of the project.
     @param name Name of product.
     @param price Price of product.
     @param stock Available amount of product.
     @param min Minimum amount allowed.
     @param max Maximum amount allowed.
     @return Returns either empty string or list of errors.
     */
    public  String logicCheck(String name, double price, int stock, int min, int max){

        /** This variable is used to compare product price to total amount of its associated parts. */
        double partTotals = 0;
        for(int i = 0; i < associatedParts.size(); i++){
            partTotals += associatedParts.get(i).getPrice();
        }

        String thingsToFix = "";
        if(name.isEmpty()){
            thingsToFix += "Must enter a product name.\n";
        }
        if(price < 0){
           thingsToFix += "Price must have a value at $0 or more.\n";
        }
        if (price <= partTotals){
            thingsToFix += "Product price must be greater than its total associated parts value.\n";
        }
        if (stock < min || stock > max){
            thingsToFix += "Stock amount must be between Minimum amount and Max amount.\n";
        }
        if (min >= max || min < 0){
            thingsToFix += "Minimum value must be less than max but not lower than 0.\n";
        }
        if (max <= min){
            thingsToFix += "Max amount must be greater than Minimum amount.\n";
        }
        if (associatedParts.size() <=0){
            thingsToFix += "Product must have at least one associated part\n";
        }
        return thingsToFix;
    }

    }





