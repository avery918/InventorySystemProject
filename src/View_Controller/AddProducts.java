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


/** This class is the add product form.
 Used to add a product to the inventory. */
public class AddProducts implements Initializable {

    /** These are the addProduct form's textfields.
     Used to retrieve user input for app. */
    @FXML
    private TextField productSearchTxtt;

    @FXML
    private TextField productIdtxt;

    @FXML
    private TextField productNametxt;

    @FXML
    private TextField productInvtxt;

    @FXML
    private TextField productPricetxt;

    @FXML
    private TextField productMaxtxt;

    @FXML
    private TextField productMintxt;


    /** These are addProduct top tableview and table columns.
     Displays all parts in the system. */
    @FXML
    private TableView<Part> addProdTopTableView;

    /** Column that displays the top table part's Id number. */
    @FXML
    private TableColumn<Part, Integer> addPartIDTopCol;

    /** Column that displays the top table part's name. */
    @FXML
    private TableColumn<Part, String> addPartNameTopCol;

    /** Column that displays the top table part's stock level. */
    @FXML
    private TableColumn<Part, Integer> addInvLevelTopCol;

    /** Column that displays the top table part's price. */
    @FXML
    private TableColumn<Part, Double> addPriceTopCol;

    /** These are addProduct bottom tableview and table columns.
     Displays the parts associated with the product.  */
    @FXML
    private TableView<Part> addProdBottomTableView;

    /** Column that displays the bottom table associated part's Id number. */
    @FXML
    private TableColumn<Part, Integer> addPartIDBottomCol;

    /** Column that displays the bottom table associated part's name. */
    @FXML
    private TableColumn<Part, String> addPartNameBottomCol;

    /** Column that displays the bottom table associated part's stock level. */
    @FXML
    private TableColumn<Part, Integer> addInvLevelBottomCol;

    /** Column that displays the bottom table associated part's price. */
    @FXML
    private TableColumn<Part, Double> addPriceBottomCol;

    Stage stage;
    Parent scene;

    /** Product object used to add a new product to the system. */
    private Product newProduct = new Product(0, null, 0, 0, 0, 0);


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /** Populates the addProdTopTableView with all available parts.  */
        addProdTopTableView.setItems(Inventory.getAllParts());
        addPartIDTopCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameTopCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvLevelTopCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceTopCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This Method searches for a part in the top table. */
    @FXML
    void searchAsscProdParts(ActionEvent event) {

        /** String variable to get user input. */
        String searchParts = productSearchTxtt.getText();

        /** ObservableList to hold any part that matches/partially matches user input. */
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();
        Part highlightPart = null;

        /** Cycling through all parts in the system. */
        for (Part findPart : Inventory.getAllParts()) {
            /** If a part match/partially matches user input add it to the selectedParts ObservableList. */
            if (findPart.getName().contains(searchParts) || Integer.toString(findPart.getId()).equals(searchParts)) {
                selectedParts.add(findPart);
                highlightPart = findPart;
            }
            /** Populates top table with the results. */
            addProdTopTableView.setItems(selectedParts);
        }
        /** If one result is return then it is highlighted. */
        if (selectedParts.size() == 1) {
            addProdTopTableView.getSelectionModel().select(highlightPart);
        }
    }

    /**This Method adds a part to product's associatedParts ObservableList. */
    @FXML
    void AddToProd(ActionEvent event) {

        /** This variable is initialize with the chosen part's data. */
        Part addPart = addProdTopTableView.getSelectionModel().getSelectedItem();

        if (addPart != null) {
            /** If a part is chosen then it is added to the product's associatedParts ObservableList. */
            newProduct.addAssociatedPart(addPart);

            /** The bottom table view is populated with any part in the associatedParts ObservableList. */
            addProdBottomTableView.setItems(newProduct.getAllAssociatedParts());

            /** These four columns will show the id, name, stock amount, and price for associated parts. */
            addPartIDBottomCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            addPartNameBottomCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addInvLevelBottomCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            addPriceBottomCol.setCellValueFactory(new PropertyValueFactory<>("price"));

            /** Resets the search text field. */
            productSearchTxtt.setText("");
            /** Repopulates the top table with all available parts*/
            addProdTopTableView.setItems(Inventory.getAllParts());
        }

    }

    /** Cancels addProduct form and returns to main menu.
        Doesn't save any data. */
    @FXML
    void CancelToMain(ActionEvent event) throws IOException {

        /** This alert ask user if the would like to return to main menu*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"The product wasn't saved. Go back to main menu?");
        Optional<ButtonType> toDelete = alert.showAndWait();
        if (toDelete.get() == ButtonType.OK)
            screenSwitch(event, "/View_Controller/MainMenu.fxml");
    }

    /** This Method removes a part from the product's associatedParts ObservableList. */
    @FXML
    void RemoveFromProd(ActionEvent event) {
        /** This variable is initialize with the part to delete data. */
        Part removePart = addProdBottomTableView.getSelectionModel().getSelectedItem();
        /** Calls the Product's class deleteAssociatedParts method.
            removePart is sent as the parameter. */
        newProduct.deleteAssociatedParts(removePart);
    }

    /** This Method is used to save new product.
        Returns to main menu after save.*/
    @FXML
    void SaveThenToMain(ActionEvent event) throws IOException {
        /** Try-catch block to catch NumberFormatException. */
        try {
            /** Flag used to check if there is a logical error or not.
                user's input used as parameter for logicCheck method.  */
            String logicProductCheck = newProduct.logicCheck(productNametxt.getText(),Double.parseDouble(productPricetxt.getText()),Integer.parseInt(productInvtxt.getText()),
                    Integer.parseInt(productMintxt.getText()),Integer.parseInt(productMaxtxt.getText()));

            /** Alert will display logical errors if string doesn't return back empty. */
            if (logicProductCheck != ""){
                Alert correctInput = new Alert(Alert.AlertType.ERROR);
                correctInput.setTitle("Logic Error!");
                correctInput.setContentText(logicProductCheck);
                correctInput.showAndWait();
            }
            else{
                /** If no logical errors are found newProduct is updated with the user's inputs. */
                newProduct.setName(productNametxt.getText());
                newProduct.setStock(Integer.parseInt(productInvtxt.getText()));
                newProduct.setPrice(Double.parseDouble(productPricetxt.getText()));
                newProduct.setMin(Integer.parseInt(productMintxt.getText()));
                newProduct.setMax(Integer.parseInt(productMaxtxt.getText()));

                /** The new Product is add to the inventory system. */
                Inventory.addProducts(newProduct);

                /** The screenSwitch called to go back to main menu. */
                screenSwitch(event, "/View_Controller/MainMenu.fxml");
            }


            }
        catch (NumberFormatException e) {
            /** Displays an alert.
             Tell user to enter correct input for each field. */
                Alert correctInput = new Alert(Alert.AlertType.ERROR);
                correctInput.setTitle("Input Error!");
                correctInput.setContentText("Please make sure to enter the correct input for each field!");
                correctInput.showAndWait();
            }

        }
    /** Method used by the SaveThenToMain and CancelToMain Methods.
     * switches page back to main menu.
     * @param event Action triggering the method call.
     * @param screen Screen to be switched back to.*/
        public void screenSwitch (ActionEvent event, String screen) throws IOException {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource(screen));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }
