package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** Main menu Screen
 *  Main screen in the inventory system*/
public class MainMenu implements Initializable {

    /** Private text fields used for searching the part's table and product's table respectively. */
    @FXML
    private TextField partSearchFieldTxt;

    @FXML
    private TextField productSearchFieldTxt;

    /** Table view used to display all parts in the inventory. */
    @FXML
    private TableView<Part> mainPartsTbleView;

    /** Column that displays the part's Id number. */
    @FXML
    private TableColumn<Part, Integer> mainPartPartIDCol;

    /** Column that displays the part's name. */
    @FXML
    private TableColumn<Part, String> mainPartPartNameCol;

    /** Column that displays the part's stock level. */
    @FXML
    private TableColumn<Part, Integer> mainPartInvLevelCol;

    /** Column that displays the part's price. */
    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;


    /** Table view used to display all parts in the inventory. */
    @FXML
    private TableView<Product> mainProdTblView;

    /** Column that displays the product's Id number. */
    @FXML
    private TableColumn<Product, Integer> mainProdPartIDCol;

    /** Column that displays the product's name. */
    @FXML
    private TableColumn<Product, String> mainProdPartNameCol;

    /** Column that displays the product's stock level. */
    @FXML
    private TableColumn<Product, Integer> mainProdInvLevelCol;

    /** Column that displays the product's price. */
    @FXML
    private TableColumn<Product, Double> mainProdPriceCol;


    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** Gets all parts in the system and populates the part's table view. */
        mainPartsTbleView.setItems(Inventory.getAllParts());

        /** Gets user input and populates the part's id, part's name, part's stock,
            and part's price columns respectively. */
        mainPartPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /** Gets all products in the system and populates the product's table view. */
        mainProdTblView.setItems(Inventory.getAllProducts());

        /** Gets user input and populates the product's id, product's name, product's stock,
         and product's price columns respectively. */
        mainProdPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProdInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    Stage stage;
    Parent scene;

    /** This Method searches for a part in the part's table view. */
    @FXML
    void searchForParts(ActionEvent event) {

        /** String variable to get user input. */
        String searchParts = partSearchFieldTxt.getText();

        /** ObservableList to hold any part that matches/partially matches user input. */
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();

        /** Variable to be used with highlighting a single part. */
        Part highlightPart = null;

        /** Cycling through all parts in the system. */
        for(Part findPart : Inventory.getAllParts()){

            /** If a part match/partially matches user input add it to the selectedParts ObservableList. */
            if(findPart.getName().contains(searchParts) || Integer.toString(findPart.getId()).equals(searchParts)){
                selectedParts.add(findPart);
                highlightPart = findPart;
            }
            /** Populates part's table with the results. */
            mainPartsTbleView.setItems(selectedParts);

            }

        /** If one result is return then it is highlighted. */
        if(selectedParts.size() == 1){
            mainPartsTbleView.getSelectionModel().select(highlightPart);
        }

    }


    /** This Method searches for a product in the product's table view. */
    @FXML
    void searchForProducts(ActionEvent event) {
        /** String variable to get user input. */
        String searchProducts = productSearchFieldTxt.getText();

        /** ObservableList to hold any product that matches/partially matches user input. */
        ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
        /** Variable to be used with highlighting a single product. */
        Product highlightProduct = null;



        /** Cycling through all products in the system. */
        for(Product findProduct : Inventory.getAllProducts()){

            /** If a product match/partially matches user input add it to the selectedProducts ObservableList. */
            if(findProduct.getName().contains(searchProducts) || Integer.toString(findProduct.getId()).equals(searchProducts)) {
                selectedProducts.add(findProduct);
                highlightProduct = findProduct;
            }
            /** Populates product's table with the results. */
            mainProdTblView.setItems(selectedProducts);
            }

        /** If one result is return then it is highlighted. */
        if(selectedProducts.size() == 1){
            mainProdTblView.getSelectionModel().select(highlightProduct);
        }

    }

    /** This method enables the user to delete a part from the inventory system. */
    @FXML
    void deletePart(ActionEvent event) {
        /** String variable  that indicates the user selected part. */
        String searchParts = partSearchFieldTxt.getText();

        if (!searchParts.isEmpty() && mainPartsTbleView.getItems().size() > 0) {
            /** A flag that indicates if a part was deleted or not. */
            boolean flag = false;

            /** Alert displays asking to delete selected part. */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete this part?");
            Optional<ButtonType> toDelete = alert.showAndWait();

            if (toDelete.get() == ButtonType.OK) {
                 /** Cycle throught all parts in the inventory. */
                for (Part findPart : Inventory.getAllParts()) {
                    if (findPart.getName().equals(searchParts) || Integer.toString(findPart.getId()).equals(searchParts)) {
                        flag = Inventory.deletePart(findPart);
                        break;
                    }
                }
            }
            if(flag == false){
                /** If the part wasn't deleted this alert lets the user know. */
                Alert notDeleted = new Alert(Alert.AlertType.CONFIRMATION);
                notDeleted.setTitle("Part Not Deleted");
                notDeleted.setContentText("No part was not deleted.");
                notDeleted.showAndWait();
            }
        }
        /** Resets the search text field to an empty string. */
        partSearchFieldTxt.setText("");
        /** Repopulates the parts table will all available parts. */
        mainPartsTbleView.setItems(Inventory.getAllParts());


    }

    /** This method enables the user to delete a product from the inventory system. */
    @FXML
    void deleteProduct(ActionEvent event) {
        /** String variable  that indicates the user selected product. */
        String searchProducts = productSearchFieldTxt.getText();

        if (!searchProducts.isEmpty() && mainProdTblView.getItems().size() > 0 ){
            /** A flag that indicates if a product was deleted or not. */
            boolean flag = false;

            /** Alert displays asking to delete selected product. */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Delete this product?");
            Optional<ButtonType> toDelete = alert.showAndWait();

            if (toDelete.get() == ButtonType.OK){
                /** Cycle throught all products in the inventory.*/
                for(Product findProduct : Inventory.getAllProducts()){
                    if(findProduct.getName().equals(searchProducts) || Integer.toString(findProduct.getId()).equals(searchProducts)){
                        flag =  Inventory.deleteProduct(findProduct);
                        break;
                    }
                }
            }
            /** If the product wasn't deleted this alert lets the user know. */
            if(flag == false){
                Alert notDeleted = new Alert(Alert.AlertType.CONFIRMATION);
                notDeleted.setTitle("Product Not Deleted");
                notDeleted.setContentText("No product was not deleted.");
                notDeleted.showAndWait();
            }
        }

        /** Resets the search text field to an empty string. */
        productSearchFieldTxt.setText("");

        /** Repopulates the products table will all available products. */
        mainProdTblView.setItems(Inventory.getAllProducts());
    }

    /** This method exit the application when the exit button is pushed. */
    @FXML
    void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    /** This method opens the add part form.*/
    @FXML
    void openAddPartForm(ActionEvent event) throws IOException {
        screenSwitch(event, "/View_Controller/AddParts.fxml");
    }

    /** This method opens the add product form.*/
    @FXML
    void openAddProductForm(ActionEvent event) throws IOException {
        screenSwitch(event, "/View_Controller/AddProducts.fxml");
    }

    /** This method takes the chosen part to be modified and opens the up
     * the modify part form. */
    @FXML
    void openModifyPartForm(ActionEvent event) throws IOException {
        /** Try-catch block to catch LoadException.*/
        try{
            /** Calls ModifyParts static method initModPart.
             * pre load the modify part screen with the part to be modified's data.*/
            ModifyParts.initModPart(mainPartsTbleView.getSelectionModel().getSelectedItem());
            screenSwitch(event, "/View_Controller/ModifyParts.fxml");
        }
        /** If no part was chosen to be modified then an alert will display.
            Alert tells user to choose a part. */
        catch (LoadException e){
            Alert forgotPart = new Alert(Alert.AlertType.WARNING);
            forgotPart.setTitle("Choose Part!");
            forgotPart.setContentText("Please choose the part to modify!");
            forgotPart.showAndWait();
        }

    }

    /** This method takes the chosen product to be modified and opens the up
     * the modify product form. */
    @FXML
    void openModifyProductForm(ActionEvent event) throws IOException {
        /** Try-catch block to catch LoadException.*/
        try{
            /** Calls ModifyProducts static method initModProduct.
             * pre load the modify product screen with the product to be modified's data.*/
            ModifyProducts.initModProduct(mainProdTblView.getSelectionModel().getSelectedItem());
            screenSwitch(event, "/View_Controller/ModifyProducts.fxml");
        }
        /** If no product was chosen to be modified then an alert will display.
         Alert tells user to choose a product. */
        catch(LoadException e){
            Alert forgotProduct = new Alert(Alert.AlertType.WARNING);
            forgotProduct.setTitle("Choose Product!");
            forgotProduct.setContentText("Please choose a product to modify!");
            forgotProduct.showAndWait();
        }
    }

    /** This method is passed a String and an ActionEvent.
        Using the two parameters it switches to the designated screen.
     *  @param event Action triggering the method call.
     *  @param screen Screen to be switched back to.
     */
    public void screenSwitch(ActionEvent event, String screen) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}