package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.util.Optional;

/** This class is the add part form.
    Used to add a part to  the inventory. */
public class AddParts {

    Stage stage;
    Parent scene;

    /** These are the addPart form's textfields.
      Used to retrieve user input for app. */
    @FXML
    private TextField partIdtxt;

    @FXML
    private TextField partNametxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMachComTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private Label machCompTxT;

    @FXML
    private RadioButton partInHouseRBtn;

    @FXML
    private RadioButton partOutsourcedRBtn;

    /** Method to set machCompTxT.
     * Displays "Machine ID" when selected. */
    @FXML
    void clickInHousebtn(ActionEvent event) {
        machCompTxT.setText("Machine ID");
    }

    /** Method to set machCompTxT.
     * Displays "Company Name" when selected. */
    @FXML
    void clickOutsourceBtn(ActionEvent event) {
        machCompTxT.setText("Company Name");
    }

    /**Method use to go back to main menu.
       Doesn't save part. */
    @FXML
    void CancelToMain(ActionEvent event) throws IOException {
        /** This alert ask user if the would like to return to main menu*/

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"The part wasn't saved. Go back to main menu?");
        Optional<ButtonType> toDelete = alert.showAndWait();
        if (toDelete.get() == ButtonType.OK)
            screenSwitch(event, "/View_Controller/MainMenu.fxml");
    }

    /**Method use to go back to main menu.
     Will save part to inventory system. */
    @FXML
    void SaveThenMainMenu(ActionEvent event) throws IOException {

        /** This variable is used as a flag for errors. */
        String logicCheck = null;

            /** Try-Catch block use to catch NumberFormatException. */
            try {
                /** If outsourced button selected. */
                if (partOutsourcedRBtn.isSelected()) {
                    /** Method used to check the logic of the user's input. */
                    logicCheck = Outsourced.logicCheck(partNametxt.getText(), Double.parseDouble(partPriceTxt.getText()),
                            Integer.parseInt(partInvTxt.getText()), Integer.parseInt(partMinTxt.getText()),
                            Integer.parseInt(partMaxTxt.getText()), partMachComTxt.getText());
                    /** Alert will display logic errors if string doesn't return back empty. */
                    if (logicCheck != "") {
                        Alert correctInput = new Alert(Alert.AlertType.ERROR);
                        correctInput.setTitle("Logic Error!");
                        correctInput.setContentText(logicCheck);
                        correctInput.showAndWait();
                    }
                    /** Will add part to inventory if no logical errors are found. */
                    else {
                        Inventory.addParts(new Outsourced(Part.partNumber,partNametxt.getText(),
                                Double.parseDouble(partPriceTxt.getText()), Integer.parseInt(partInvTxt.getText()),
                                Integer.parseInt(partMinTxt.getText()), Integer.parseInt(partMaxTxt.getText()),
                                partMachComTxt.getText()));
                        /** Method to switch back to main menu. */
                        screenSwitch(event, "/View_Controller/MainMenu.fxml");
                    }
                }
                /** If No button or InHouse button selected*/
                else{
                    /** Alert will display logic errors if string doesn't return back empty. */
                    logicCheck = InHouse.inHouseLogicCheck(partNametxt.getText(),Double.parseDouble(partPriceTxt.getText()),
                                Integer.parseInt(partInvTxt.getText()),Integer.parseInt(partMinTxt.getText()),
                                Integer.parseInt(partMaxTxt.getText()));

                    /** Alert will display logic errors in string returns back not empty. */
                    if (logicCheck != ""){
                            Alert correctInput = new Alert(Alert.AlertType.ERROR);
                            correctInput.setTitle("Logic Error!");
                            correctInput.setContentText(logicCheck);
                            correctInput.showAndWait();
                    }
                    /** Will add part to inventory if no logic error is found. */
                    else{
                        Inventory.addParts(new InHouse(Part.partNumber,partNametxt.getText(),
                                Double.parseDouble(partPriceTxt.getText()),Integer.parseInt(partInvTxt.getText()),
                                Integer.parseInt(partMinTxt.getText()),Integer.parseInt(partMaxTxt.getText()),
                                Integer.parseInt(partMachComTxt.getText()) ));

                        /** Method to switch back to main menu. */
                        screenSwitch(event, "/View_Controller/MainMenu.fxml");
                    }
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
     /** Method used by the SaveThenMainMenu and CancelToMain Methods.
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
