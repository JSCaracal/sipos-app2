package inventory;

import org.jsoup.Jsoup;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class Inventory {
    private Map<String,InventoryItem> inventoryMap;
    private ArrayList<InventoryItem> inventoryArray;

    public Inventory(){

    }
    InventoryItem createInventoryItem(String serialNumber, String name, double price){
        InventoryItem item = new InventoryItem(serialNumber,name,price);
        return item;
    }
    //Adds an item to both map and Array
    void addItem(InventoryItem item){
            //Add item to map

            //Add item to Array
    }

    void removeItem(InventoryItem item){
        //Retrieve object using the map key
        //Find object in array with .get()
        //Remove object from array

        //Use key to remove item in map


    }

    void clearList(){
        //.clear() for map and array
    }

    void editItemName(String newName){
        //Find key using item name
        //Use the object retrieved from key to search in array

        //Set name
    }

    void editSerialNumber(String newSerial){
        //Find key using item name
        //Use the object retrieved from key to search in array

        //Set name
    }

    void readFile(File file){
        //Try
        //If file is .tsb
        //Call the TSB method
        //Else if the file is .html
        //Call the htmlReader() method
        //Else if .json
        //Call the jsonReader() method
        //Catch fileIOException

    }

    void writeFile(File file){
        //If file is saved .tsb
        //Call tsbWriter
        //Else if .html
        //Call .htmlWriter
        //Else .jsonWriter
    }
    void tsbReader(){
        //Clear the current Inventory to make way for the new Inventory
        //Create scanner  for each line
        //Create Scanner for each value in the line
        //While nextLine
            //Data reader scans each line while using the delimiter "\t"
            //While there is hasNext(), it will be stored in a string
                //if the index is 0, the string will be stored in a new item serial number
                //if 1 string will be stored in name
                //if 2 String will be parsed into a double to be stored in itemPrice
                //Index++
            //Index = 0
            //Add item to list

    }
    void htmlReader(){
        //Clear inventory
        //Create scanner to read each line
        //Use Jsoup to read html file
        //Todo figure out Jsoup
    }

    void jsonReader(){
        //Clear Inventory
        //Todo relearn GSON
    }

    void tsbWriter(){
        //Save the file using a formatted string
    }
    void htmlWriter(){
        //Save to file using table format
        //Or use Jsoup
    }
    void jsonWriter(){
        //Save using GSON
    }


}
