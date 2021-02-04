package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** This class is the modify part form.
 Used to modify a part in the inventory. */
public class ModifyParts implements Initializable {

    /** These are the modPart form's textfields.
     Used to retrieve user input. */
    @FXML
    private Label machCompTxt;

    @FXML
    private TextField ModPartIDTxt;

    @FXML
    private TextField ModPartNameTxt;

    @FXML
    private TextField ModPartInvTxt;

    @FXML
    private TextField ModPartPriceTxt;

    @FXML
    private TextField ModPartMaxTxt;

    @FXML
    private TextField ModPartMachIDTxt;

    @FXML
    private TextField ModPartMinTxt;

    @FXML
    private RadioButton modPartInHouseRBtn;

    @FXML
    private RadioButton modPartOutsourcedBtn;

    Stage stage;
    Parent scene;

    /** Static variable used to modify a selected part. */
    private static Part modPart = null;

    /** Static method to initialize modPArt.
        Gets the initialization data from selected part to modify
        @param addToModPart Part whose data is used to initialize modPart to. */
    public static void initModPart(Part addToModPart){
        modPart = addToModPart;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

             /** These text fields are pre-populated data from modPart. */
             ModPartIDTxt.setText(Integer.toString(modPart.getId()));
             ModPartNameTxt.setText(modPart.getName());
             ModPartInvTxt.setText(Integer.toString(modPart.getStock()));
             ModPartPriceTxt.setText(Double.toString(modPart.getPrice()));
             ModPartMaxTxt.setText(Integer.toString(modPart.getMax()));
             ModPartMinTxt.setText(Integer.toString(modPart.getMin()));

             /** Checks to see if modPart is an outsourced object.
              ModPartMachIDTxt populated with company's name if so. */
             if(modPart instanceof Outsourced){
                 machCompTxt.setText("Company Name");
                 modPartOutsourcedBtn.setSelected(true);
                 ModPartMachIDTxt.setText(((Outsourced) modPart).getCompanyName());
             }
             /** Checks to see if modPart is an InHouse object
              ModPartMachIDTxt populated with MachineId if so. */
             else if(modPart instanceof InHouse){
                 modPartInHouseRBtn.setSelected(true);
                 ModPartMachIDTxt.setText(Integer.toString(((InHouse) modPart).getMachineId()));
             }
    }

    /**Method use to go back to main menu.
     Part has not been modified. */
    @FXML
    void CancelToMain(ActionEvent event) throws IOException {
        /** This alert ask user if the would like to return to main menu*/
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"No changes took place. Go back to main menu?");
        Optional<ButtonType> toDelete = alert.showAndWait();
        if (toDelete.get() == ButtonType.OK)
            screenSwitch(event, "/View_Controller/MainMenu.fxml");
    }

    /** Method use to go back to main menu.
     Will update and save part to inventory system. */
    @FXML
    void SaveThenToMain(ActionEvent event) throws IOException {
        /** This variable is used as a flag for errors. */
        String logicCheck = null;

        /** Try-Catch block use to catch NumberFormatException. */
        try{
            /** If modPart is an Outsourced object. */
            if(modPart instanceof Outsourced){
                /** Info in ModPartMachIDTxt is companyName info. */
                ModPartMachIDTxt.setText(((Outsourced) modPart).getCompanyName());

                /** Method used to check the logic of the user's input. */
                logicCheck = Outsourced.logicCheck(ModPartNameTxt.getText(), Double.parseDouble(ModPartPriceTxt.getText()),
                        Integer.parseInt(ModPartInvTxt.getText()), Integer.parseInt(ModPartMinTxt.getText()),
                        Integer.parseInt(ModPartMaxTxt.getText()), ModPartMachIDTxt.getText());
                /** Alert will display logic errors if string doesn't return back empty. */
                if (logicCheck != ""){
                    Alert correctInput = new Alert(Alert.AlertType.ERROR);
                    correctInput.setTitle("Logic Error!");
                    correctInput.setContentText(logicCheck);
                    correctInput.showAndWait();
                }
                /** Will update part with new info if no logical errors are found. */
                else{
                    modPart.setId(Integer.parseInt(ModPartIDTxt.getText()));
                    modPart.setName( ModPartNameTxt.getText());
                    modPart.setStock(Integer.parseInt(ModPartInvTxt.getText()));
                    modPart.setPrice(Double.parseDouble(ModPartPriceTxt.getText()));
                    modPart.setMax(Integer.parseInt(ModPartMaxTxt.getText()));
                    modPart.setMin(Integer.parseInt(ModPartMinTxt.getText()));
                    ((Outsourced) modPart).setCompanyName(ModPartMachIDTxt.getText());
                    screenSwitch(event, "/View_Controller/MainMenu.fxml");
                }
            }
            /** If modPart is an IhHouse object. */
            else {
                /** Info in ModPartMachIDTxt is machineId ID. */
                ModPartMachIDTxt.setText(Integer.toString(((InHouse) modPart).getMachineId()));

                /** Alert will display logic errors if string doesn't return back empty. */
                logicCheck = InHouse.inHouseLogicCheck(ModPartNameTxt.getText(), Double.parseDouble(ModPartPriceTxt.getText()),
                        Integer.parseInt(ModPartInvTxt.getText()), Integer.parseInt(ModPartMinTxt.getText()),
                        Integer.parseInt(ModPartMaxTxt.getText()));
                if (logicCheck != ""){
                    Alert correctInput = new Alert(Alert.AlertType.ERROR);
                    correctInput.setTitle("Logic Error!");
                    correctInput.setContentText(logicCheck);
                    correctInput.showAndWait();
                }
                /** Will update part with new info if no logical errors are found. */
                else{
                    modPart.setId(Integer.parseInt(ModPartIDTxt.getText()));
                    modPart.setName( ModPartNameTxt.getText());
                    modPart.setStock(Integer.parseInt(ModPartInvTxt.getText()));
                    modPart.setPrice(Double.parseDouble(ModPartPriceTxt.getText()));
                    modPart.setMax(Integer.parseInt(ModPartMaxTxt.getText()));
                    modPart.setMin(Integer.parseInt(ModPartMinTxt.getText()));
                    ((InHouse) modPart).setMachineId(Integer.parseInt(ModPartMachIDTxt.getText()));
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
