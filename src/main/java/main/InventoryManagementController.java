package main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class InventoryManagementController {

    @FXML
    private Button bttnAdd;

    @FXML
    private Button bttnDelete;

    @FXML
    private TableView<?> mainTableView;

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
    private TableColumn<?, ?> tableColName;

    @FXML
    private TableColumn<?, ?> tableColPrice;

    @FXML
    private TableColumn<?, ?> tableColSerialNumber;

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

}
