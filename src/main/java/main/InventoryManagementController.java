package main;
import inventory.Inventory;
import inventory.InventoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class InventoryManagementController implements Initializable {
    Inventory inventoryList = new Inventory();
    ObservableList<InventoryItem> obList = FXCollections.observableArrayList();
    NumberFormat currency = NumberFormat.getCurrencyInstance();

    @FXML
    private Button bttnAdd;

    @FXML
    private Button bttnDelete;

    @FXML
    private TableView<InventoryItem> mainTableView;

    @FXML
    private MenuItem menuItemClearList;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemHelp;

    @FXML
    private MenuItem menuItemSave;

    @FXML
    private MenuItem menuOpenFile;

    @FXML
    private TableColumn<InventoryItem, String> tableColName;

    @FXML
    private TableColumn<InventoryItem, Double> tableColPrice;

    @FXML
    private TableColumn<InventoryItem, String> tableColSerialNumber;

    @FXML
    private TextField textFieldNameInput;

    @FXML
    private TextField textFieldNameSearch;

    @FXML
    private TextField textFieldPriceAdd;

    @FXML
    private TextField textFieldSerialInput;

    @FXML
    private TextField textFieldSerialSearch;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableColName.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,String>("name")
        );

        tableColPrice.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,Double>("price")
        );

        tableColSerialNumber.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,String>("serialNumber")
        );
        tableColPrice.setCellFactory(tc->new TableCell<InventoryItem,Double>(){
            @Override
            protected void updateItem(Double price, boolean empty){
                super.updateItem(price,empty);
                if(empty){
                    setText(null);
                }else {
                    setText(currency.format(price));
                }
            }
                }
        );
    }

    @FXML
    void addItemToList(ActionEvent event) {
        InventoryItem item = getUserEnteredInfo();
        //Prevents errors
        if(item == null){
            return;
        }
        inventoryList.addItem(item);
        mainTableView.setItems(inventoryList.getInventoryObList());


    }
    //Goes through the objects in the list menu and deletes them from view
    //along with all from the maps and stuff
    @FXML
    void clearListMenu(ActionEvent event) {

    }

    @FXML
    //Literaly closes theprogram
    void closeProgram(ActionEvent event) {

    }

    @FXML
    void editName(ActionEvent event) {
        //get the text input from the tableView
        //Call the editname function
        //Once enter is pressed update tableview

    }

    @FXML
    void editPrice(ActionEvent event) {
        //get the text input from the tableView
        //Call the editPrice function
        //Once enter is pressed update tableview
    }

    @FXML
    void editSerialNumber(ActionEvent event) {
        //get the text input from the tableView
        //Call the editSerialNumber function
        //Once enter is pressed update tableview
    }

    @FXML
    void loadFile(ActionEvent event) {
        //User menu box to retrieve file
        //call the readFile method, has the logic required
        //Clear tableView
    }

    @FXML
    void openTut(ActionEvent event) {
        //Opens help file to be helpful :)
    }
    @FXML
    void removeItemFromList(ActionEvent event) {
        //Make an observible list
        ObservableList<InventoryItem> selection,all;
        //Get selected item from tableView
        all = mainTableView.getItems();
        selection = mainTableView.getSelectionModel().getSelectedItems();
        //Retrieves it from Array list using object/index of
        for(int i = 0; i < selection.size(); i++){
            InventoryItem item = inventoryList.getInventoryArray().get(inventoryList.getInventoryArray().indexOf(selection.get(i)));
            //Call remove function for backend
            inventoryList.removeItem(item);
        }

        //product selected and remove
        selection.forEach(all::remove);
    }

    @FXML
    void saveInventory(ActionEvent event) {
        //User menubox
        //Call the writeFile method, has required logic
    }

    @FXML
    void searchForName(KeyEvent event) {
        //Called when every key is pressed
        //Calls a searchMethod
    }

    @FXML
    void searchForSerial(KeyEvent event) {
        //Called when every key is pressed
        //Calls a searchMethod
    }

    /**
     * Helper Functions
     */
    //Retrives the user info,and places it into an InventoryItem object
    //Return null if any user info entered was invalid
    InventoryItem getUserEnteredInfo(){
        //Store strings in an Array
        String[] userInfo = new String[3];
        userInfo[0] = textFieldSerialInput.getText();
        userInfo[1] = textFieldNameInput.getText();
        userInfo[2] = textFieldPriceAdd.getText();

        //Parse Double
        double price;
        try {
             price = Double.parseDouble(userInfo[2]);
        }catch (NumberFormatException e){
            alertNotification("The price format was not valid, try again");
            return null;
        }
        //Error Handle Text Fields
        if(inventoryList.isValidSerial(userInfo[0]) == false){
            alertNotification("Please make sure the serial you entered is in the format " +
                    "A-XXX-XXX-XXX, A must be a letter");
            return null;
        }
        if(userInfo[1].length() > 256 || userInfo[1].length() < 2){
            alertNotification("The Item Name was either too short or too long");
            return null;
        }
        InventoryItem item = new InventoryItem(userInfo[0],userInfo[1],price);
        textFieldPriceAdd.clear();
        textFieldNameInput.clear();
        textFieldSerialInput.clear();
        return item;
    }
    //Displays error
    void alertNotification(String errorMessage){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("ERROR! ERROR!");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

}
