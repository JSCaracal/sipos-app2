//Filtered Data turotial found at https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/

package main;
import inventory.Inventory;
import inventory.InventoryItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
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
    private Button bttnConfirmPriceEdit;

    @FXML
    private TableView<InventoryItem> mainTableView;

    @FXML
    private MenuBar mainMenuBar;

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

    @FXML
    private TextField editPricePrompt;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableColName.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,String>("name")
        );
        tableColName.setCellFactory(TextFieldTableCell.forTableColumn());

        /**tableColPrice.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,Double>("price")
        );**/

        tableColSerialNumber.setCellValueFactory(
                new PropertyValueFactory<InventoryItem,String>("serialNumber")
        );
        tableColSerialNumber.setCellFactory(TextFieldTableCell.forTableColumn());


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


        FilteredList<InventoryItem> filteredData = new FilteredList<>(inventoryList.getInventoryObList(), p -> true);

        // 2. Set the filter Predicate whenever the filter changes.


        textFieldSerialSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(InventoryItem -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (InventoryItem.getSerialNumber().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        textFieldNameSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(InventoryItem -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (InventoryItem.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<InventoryItem> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(mainTableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        mainTableView.setItems(sortedData);

    }

    @FXML
    void addItemToList(ActionEvent event) {
        InventoryItem item = getUserEnteredInfo();
        //Prevents errors
        if(item == null || inventoryList.getInventoryArray().size() >1024){
            return;
        }
        inventoryList.addItem(item);


    }
    //Goes through the objects in the list menu and deletes them from view
    //along with all from the maps and stuff
    @FXML
    void clearListMenu(ActionEvent event) {
        inventoryList.clearList();
        mainTableView.refresh();
    }

    @FXML
    //Literaly closes theprogram
    void closeProgram(ActionEvent event) {
        InventoryItem item1 = new InventoryItem("j-0c3-ow2-4dx","Gatorade",2.30);
        InventoryItem item2 = new InventoryItem("h-0c5-ow3-4dx","Sprite",2.00);
        inventoryList.addItem(item1);
        inventoryList.addItem(item2);
    }

    @FXML
    void editName(TableColumn.CellEditEvent<InventoryItem,String>event) {

        //get the text input from the tableView
        InventoryItem item = event.getRowValue();
        String newName = event.getNewValue();
        //Call the editname function
        inventoryList.editItemName(newName,item.getSerialNumber());
        //inventoryList.editItemName(event.getNewValue(),item.getName());
        //Once enter is pressed update tableview


    }


    @FXML
    void editSerialNumber(TableColumn.CellEditEvent<InventoryItem,String> event) {
        //get the text input from the tableView
        InventoryItem item = event.getRowValue();
        String newSerial = event.getNewValue();
        //Error handle Bad Serial
        if(inventoryList.isValidSerial(newSerial) == false){
            alertNotification("Please make sure the serial you entered is in the format" +
                    " A-XXX-XXX-XXX, A must be a letter");
            return;
        }
        //Call the editSerialNumber function
        inventoryList.editSerialNumber(newSerial,item);
        //Once enter is pressed update tableview
        mainTableView.refresh();
    }

    @FXML
    void editPrice(ActionEvent event){
            InventoryItem item = mainTableView.getSelectionModel().getSelectedItem();
            if(editPricePrompt.getText().equals(null)){
                alertNotification("The price cannot be left empty");
                return;
            }
            double newPrice;
            try {
                newPrice = Double.parseDouble(editPricePrompt.getText());
            }catch (NumberFormatException e){
                alertNotification("Make sure you formatted the price corecctly.");
                return;
            }
            inventoryList.editPrice(newPrice,item.getSerialNumber());
            editPricePrompt.clear();
            mainTableView.refresh();
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
            mainTableView.getItems().remove(item);
        }

        //product selected and remove
    }

    @FXML
    void saveInventory(ActionEvent event) {
        //User menubox
        FileChooser fileChooser = new FileChooser();
        try {
            Window stage = mainMenuBar.getScene().getWindow();
            fileChooser.setTitle("Save Inventory");
            fileChooser.setInitialFileName("InventoryList");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSB File","*.tsb")
                    ,new FileChooser.ExtensionFilter("HTML File","*.html"),new FileChooser.ExtensionFilter("JSON file","*" +
                            ".json"));
            //Call the writeFile method, has required logic
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file);
            inventoryList.writeFile(file);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

    }

    @FXML
    void loadFile(ActionEvent event) {
        //User menu box to retrieve file
        FileChooser fileChooser = new FileChooser();
        try {
            Window stage = mainMenuBar.getScene().getWindow();
            fileChooser.setTitle("Load Inventory");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSB File","*.tsb")
                    ,new FileChooser.ExtensionFilter("HTML File","*.html"),new FileChooser.ExtensionFilter("JSON file","*" +
                            ".json"));
            //call the readFile method, has the logic required
            File file = fileChooser.showOpenDialog(stage);
            fileChooser.setInitialDirectory(file);
            inventoryList.readFile(file);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }

        //Clear tableView
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
