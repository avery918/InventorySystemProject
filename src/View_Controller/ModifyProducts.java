package View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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


/** This class is the modify product form.
 Used to modify a product in the inventory. */
public class ModifyProducts implements Initializable {


    /** These are the modProduct form's textfields.
     Used to retrieve user input. */
    @FXML
    private TextField modProductSearchTxtt;

    @FXML
    private TextField modProductIdtxt;

    @FXML
    private TextField modProductNametxt;

    @FXML
    private TextField modProductInvtxt;

    @FXML
    private TextField modProductPricetxt;

    @FXML
    private TextField modProductMaxtxt;

    @FXML
    private TextField modProductMintxt;

    /** These are modProduct top tableview  and table columns.
     Displays all parts in the system. */
    @FXML
    private TableView<Part> modProdTopTableView;

    /** Column that displays the top table part's ID number. */
    @FXML
    private TableColumn<Part, Integer> modPartIDTopCol;

    /** Column that displays the top table part's name. */
    @FXML
    private TableColumn<Part, String> modPartNameTopCol;

    /** Column that displays the top table part's stock level. */
    @FXML
    private TableColumn<Part, Integer> modInvLevelTopCol;

    /** Column that displays the top table part's price. */
    @FXML
    private TableColumn<Part, Double> modPriceTopCol;


    /** These are modProduct bottom tableview and table columns.
     Displays the parts associated with the product.  */
    @FXML
    private TableView<Part> modProdBottomTableView;

    /** Column that displays the bottom table associated part's ID number. */
    @FXML
    private TableColumn<Part, Integer> modPartIDBottomCol;

    /** Column that displays the bottom table associated part's name. */
    @FXML
    private TableColumn<Part, String> modPartNameBottomCol;

    /** Column that displays the bottom table associated part's stock level. */
    @FXML
    private TableColumn<Part, Integer> modInvLevelBottomCol;

    /** Column that displays the bottom table associated part's price. */
    @FXML
    private TableColumn<Part, Double> modPriceBottomCol;

    Stage stage;
    Parent scene;

    /** Static variable use to modify an existing product. */
    private static Product modProduct = null;

    /** Initialize modProduct with the select product's data.
     @param addToModProduct Product whose data is used to initialize modProduct to. */
    public static void initModProduct(Product addToModProduct){
        modProduct = addToModProduct;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        /** These text fields  are pre-populated  with data from modProduct*/
        modProductIdtxt.setText(Integer.toString(modProduct.getId()));
        modProductNametxt.setText(modProduct.getName());
        modProductInvtxt.setText(Integer.toString(modProduct.getStock()));
        modProductPricetxt.setText(Double.toString(modProduct.getPrice()));
        modProductMaxtxt.setText(Integer.toString(modProduct.getMax()));
        modProductMintxt.setText(Integer.toString(modProduct.getMin()));

        /** Populates the modProdTopTableView with all available parts.  */
        modProdTopTableView.setItems(Inventory.getAllParts());
        modPartIDTopCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartNameTopCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modInvLevelTopCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPriceTopCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /** Populates the modProdBottomTableView with any part that is associated with the product to be modified.  */
        modProdBottomTableView.setItems(modProduct.getAllAssociatedParts());
        modPartIDBottomCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartNameBottomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modInvLevelBottomCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPriceBottomCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This Method searches for a part in the top table*/
        @FXML
        void searchAsscProdParts(ActionEvent event) {
            /** String variable to get user input*/
            String searchParts = modProductSearchTxtt.getText();

            /** ObservableList to hold any part that matches/partially matches user input. */
            ObservableList<Part> selectedParts = FXCollections.observableArrayList();
            Part highlightPart = null;

            /** Cycling through all parts in the system*/
            for(Part findPart: Inventory.getAllParts()){
                if(findPart.getName().contains(searchParts) || Integer.toString(findPart.getId()).equals(searchParts)) {
                    selectedParts.add(findPart);
                    highlightPart = findPart;
                }
                /** Populates top table with the results. */
                modProdTopTableView.setItems(selectedParts);
            }
            /** If one result is return then it is highlighted. */
            if(selectedParts.size() == 1){
                modProdTopTableView.getSelectionModel().select(highlightPart);
            }
        }

    /** This Method adds a part to product's associatedParts ObservableList. */
    @FXML
    void AddToProd(ActionEvent event) {

        /** This variable is initialize with the chosen part's data. */
        Part addPart = modProdTopTableView.getSelectionModel().getSelectedItem();


        if(addPart != null){
            /** If a part is chosen then it is added to the product's associatedParts ObservableList. */
            modProduct.addAssociatedPart(addPart);

            /** The bottom table view is populated with any part in the associatedParts ObservableList. */
            modProdBottomTableView.setItems(modProduct.getAllAssociatedParts());

            /** These four columns will show the id, name, stock amount, and price for associated parts. */
            modPartIDBottomCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            modPartNameBottomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            modInvLevelBottomCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            modPriceBottomCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            /** Resets the search text field. */
            modProductSearchTxtt.setText("");

            /** Repopulates the top table with all available parts*/
            modProdTopTableView.setItems(Inventory.getAllParts());}
    }

    /** This Method removes a part from the product's associatedParts ObservableList. */
    @FXML
    void RemoveFromProd(ActionEvent event) {
        /** This variable is initialize with the part to delete data. */
        Part removePart = modProdBottomTableView.getSelectionModel().getSelectedItem();

        /** Calls the Product's class deleteAssociatedParts method.
         removePart is sent as the parameter. */
        modProduct.deleteAssociatedParts(removePart);
    }

    /** This Method is used to modify the selected product.
     Returns to main menu after save.*/
    @FXML
    void SaveThenToMain(ActionEvent event) throws IOException {

        /** Try-catch block to catch NumberFormatException. */
        try{

            /** Flag used to check if there is a logical error or not.
             user's input used as parameter for logicCheck method.  */
            String logicModProductCheck = modProduct.logicCheck(modProductNametxt.getText(),Double.parseDouble(modProductPricetxt.getText()),
                    Integer.parseInt(modProductInvtxt.getText()), Integer.parseInt(modProductMintxt.getText()),
                    Integer.parseInt(modProductMaxtxt.getText()));

            /** Alert will display logical errors if string doesn't return back empty. */
            if (logicModProductCheck != ""){
                Alert correctInput = new Alert(Alert.AlertType.ERROR);
                correctInput.setTitle("Logic Error!");
                correctInput.setContentText(logicModProductCheck);
                correctInput.showAndWait();
            }
            else {
                /** If no logical errors are found modProduct is updated with the user's inputs. */
                modProduct.setId(Integer.parseInt(modProductIdtxt.getText()));
                modProduct.setName(modProductNametxt.getText());
                modProduct.setStock(Integer.parseInt(modProductInvtxt.getText()));
                modProduct.setPrice(Double.parseDouble(modProductPricetxt.getText()));
                modProduct.setMax(Integer.parseInt(modProductMaxtxt.getText()));
                modProduct.setMin(Integer.parseInt(modProductMintxt.getText()));

                /** The screenSwitch called to go back to main menu. */
                screenSwitch(event, "/View_Controller/MainMenu.fxml");
            }
        }
        catch(NumberFormatException e){
            /** Displays an alert.
             Tell user to enter correct input for each field. */
            Alert correctInput = new Alert(Alert.AlertType.ERROR);
            correctInput.setTitle("Input Error!");
            correctInput.setContentText("Please make sure to enter the correct input for each field!");
            correctInput.showAndWait();
        }
    }

    /** Cancels modify product form and returns to main menu.
     Doesn't save any data. */
    @FXML
    void CancelToMain(ActionEvent event) throws IOException {
        /** This alert ask user if the would like to return to main menu*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"No changes took place. Go back to main menu?");
        Optional<ButtonType> toDelete = alert.showAndWait();
        if (toDelete.get() == ButtonType.OK)
            screenSwitch(event, "/View_Controller/MainMenu.fxml");
    }

    /** Method used by the SaveThenToMain and CancelToMain Methods.
     * switches page back to main menu.
     * @param event Action triggering the method call.
     * @param screen Screen to be switched back to.
     */
    public void screenSwitch(ActionEvent event, String screen) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource(screen));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
