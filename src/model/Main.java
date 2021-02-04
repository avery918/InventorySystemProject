/*
* Ryan Boyd
* Student id: 001393137
* Course: C482
*
* */



package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;


public class Main extends Application {

    /** Method that starts up the inventory system.*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/MainMenu.fxml"));
        primaryStage.setTitle("Inventory");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }



    public static void main(String[] args) {
        /** Indicator to make sure the initial data loads only once. */
        boolean flag = true;


        if(flag){
            /** Instantiations of inhouse and outsourced objects. */
            InHouse inhouse1 = new InHouse(8,"wheel",15.99,15,0,30,9875);
            InHouse inhouse2 = new InHouse(7,"handle",5.99,15,0,30,9876);
            Outsourced outsourced = new Outsourced(6,"frame",36.99,15,0,30,"bike inc");

            /** Instantiations of new Product objects. */
            Product newProduct = new Product(1,"bike", 159.99,15,1,15);
            Product newProduct2 = new Product(1,"tricycle", 259.99,5,0,10);

            /** Method to add products to the product ObservableList. */
            Inventory.addProducts(newProduct);
            Inventory.addProducts(newProduct2);

            /** Method to add parts to the part ObservableList. */
            Inventory.addParts(inhouse1);
            Inventory.addParts(inhouse2);
            Inventory.addParts(outsourced);

            flag = false;
        }


        launch(args);
    }
}
