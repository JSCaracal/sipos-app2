package inventory;

import com.google.gson.Gson;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Inventory {
    private Map<String,InventoryItem> inventoryMap;
    private ArrayList<InventoryItem> inventoryArray;
    private ObservableList<InventoryItem> inventoryObList;

    public Inventory() {
        this.inventoryArray = new ArrayList<InventoryItem>();
        this.inventoryMap = new HashMap<String, InventoryItem>();
        this.inventoryObList = FXCollections.observableArrayList();
    }
    InventoryItem createInventoryItem(String serialNumber, String name, double price){
        InventoryItem item = new InventoryItem(serialNumber,name,price);
        return item;
    }
    //Adds an item to both map and Array
    public void addItem(InventoryItem item){
            //Add item to map
            this.inventoryMap.put(item.getSerialNumber(),item);
            //Add item to Array
            this.inventoryArray.add(item);
            //Add item to ObserList
            this.inventoryObList.add(item);
    }

    public String searchByName(String name){
        for (InventoryItem item:this.getInventoryArray()) {
            if(item.getName().equals(name)){
                return item.getName();
            }
        }
        return "";
    }

    public void removeItem(InventoryItem item){
        //Retrieve object using the map key
        this.inventoryMap.remove(item.getSerialNumber());
        //Find object in array with .get()
        //Remove object from array
        this.inventoryArray.remove(item);
        //Use key to remove item in map
        //Remove from Oblist
        this.inventoryObList.remove(item);

    }

    public void clearList(){
        //.clear() for map and array
        this.inventoryArray.clear();
        this.inventoryMap.clear();
        this.inventoryObList.clear();
    }

    public void editItemName(String newName,String serialNumber){
        //Find key using item name
        InventoryItem item = inventoryMap.get(serialNumber);
        //Use the object retrieved from key to search in array
        this.inventoryMap.get(serialNumber).setName(newName);
        //Set name
        this.inventoryArray.get(this.inventoryArray.indexOf(item)).setName(newName);
        this.inventoryObList.get(this.inventoryObList.indexOf(item)).setName(newName);
    }

    public void editSerialNumber(String newSerial,InventoryItem item){
        //Find key using item name
        String oldSerial = this.inventoryMap.get(item.getSerialNumber()).getSerialNumber();
        this.inventoryArray.get(this.inventoryArray.indexOf(item)).setSerialNumber(newSerial);
        //Use the object retrieved from key to search in array
        this.inventoryMap.remove(oldSerial);
        this.inventoryMap.put(newSerial,item);
        //Set name
        this.inventoryObList.get(this.inventoryObList.indexOf(item)).setSerialNumber(newSerial);
    }

    public void editPrice(double newPrice, String serialNumber){
        //Get object for matching purposes
        InventoryItem item = this.inventoryMap.get(serialNumber);
        //Change price in Map
        this.inventoryMap.get(serialNumber).setPrice(newPrice);
        //Change in Oblist
        this.inventoryObList.get(this.inventoryObList.indexOf(item)).setPrice(newPrice);
        //Change in ArrayList
        this.inventoryArray.get(this.inventoryArray.indexOf(item)).setPrice(newPrice);
    }

   public boolean isValidSerial(String serial){
        if(serial.length() != 13){
            return false;
        }
        char firstIndex = serial.charAt(0);
        if(firstIndex < 65 || firstIndex > 90 && firstIndex < 97 || firstIndex > 122){
            return false;
        }
        for(int i = 1; i < serial.length(); i+=4){

                if(serial.charAt(i) != 45){
                    return false;
                }
        }
        return true;
    }

    public boolean isSerialSame(String serial){
        if(this.inventoryMap.get(serial) != null){
            return true;
        }
        return false;
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

    public void writeFile(File file){
        String filePath = file.toString();
        //If file is saved .tsb
        if(filePath.endsWith(".tsb")){
            //Call tsbWriter
            tsbWriter(file);
            return;
        }
        //Else if .html
        else if(filePath.endsWith(".html")){
            //Call .htmlWriter
            htmlWriter(file);
            return;
        }
        //Else .jsonWriter
        else if(filePath.endsWith(".json")) {
            jsonWriter(file);
            return;
        }
        else {
            return;
        }


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

    void tsbWriter(File file){
        //Save the file using a formatted string
        try {
            FileWriter writer = new FileWriter(file);
            for (InventoryItem item:this.inventoryObList) {
                writer.write(item.getSerialNumber()+"\t"+item.getName()+"\t"+item.getPrice()+"\n");
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void htmlWriter(File file){
        //Save to file using table format
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("<table>\n<tr>\n<th>Serial Number</th>\n<th>Name</th>\n<th>Price</t>\n</tr>");
            for(InventoryItem item:this.inventoryObList){
                writer.write("<tr>\n");
                writer.write("<td>"+item.getSerialNumber()+"</td>\n");
                writer.write("<td>"+item.getName()+"</td>\n");
                writer.write("<td>"+item.getPrice()+"</td>\n");
                writer.write("</tr>");
            }
            writer.write("</table>");
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
        //Or use Jsoup
    }
    void jsonWriter(File file){

        //Save using GSON
        Gson gson = new Gson();
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(this.inventoryArray,writer);
            writer.close();
        }catch (IOException e){

        }

    }

    public Map<String, InventoryItem> getInventoryMap() {
        return inventoryMap;
    }

    public void setInventoryMap(Map<String, InventoryItem> inventoryMap) {
        this.inventoryMap = inventoryMap;
    }

    public ArrayList<InventoryItem> getInventoryArray() {
        return inventoryArray;
    }

    public void setInventoryArray(ArrayList<InventoryItem> inventoryArray) {
        this.inventoryArray = inventoryArray;
    }

    public ObservableList<InventoryItem> getInventoryObList() {
        return inventoryObList;
    }

    public void setInventoryObList(ObservableList<InventoryItem> inventoryObList) {
        this.inventoryObList = inventoryObList;
    }

}
