package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Inventory {

    /** This ObservableList will hold all parts added to the inventory system. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** This ObservableList will hold all products added to the inventory system. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();



    /** Methods use to add a part to the allParts ObservableList.
     * @param newPart Part to be added to inventory system. */
    public static void addParts(Part newPart){
        allParts.add(newPart);
    }

    /** Methods use to add a product to the allProducts ObservableList
     * @param newProduct Product to be added to inventory system. */
    public static void addProducts(Product newProduct){
        allProducts.add(newProduct);

    }


    /** Method use to indicate if the selected part was deleted or not.
        @param selectedPart Part to be deleted.
        @return returns true if part was deleted else return false
     * */
    public static boolean deletePart(Part selectedPart){
        if(selectedPart != null){
            Inventory.allParts.remove(selectedPart);
            return true;
        }
        return false;
    }
    /** Method use to  the selected product and remove all its associated parts.
        @param selectedProduct Product to be delete.
        @return  return if product's deleted else return false. */
    public static boolean deleteProduct(Product selectedProduct){
        if (selectedProduct != null){
            selectedProduct.getAllAssociatedParts().removeAll();
            Inventory.allProducts.remove(selectedProduct);
            return true;
        }
        return false;
    }

    /** Returns all parts that are in the inventory system.
     * @return Returns all parts in the inventory. */
    public static ObservableList<Part>  getAllParts(){
        return allParts;
    }

    /** Returns all products that are in the inventory system.
     *  @return Returns all products in the inventory.*/
    public static ObservableList<Product>  getAllProducts(){
        return allProducts;
    }

}