package inventory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

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
        return new InventoryItem(serialNumber,name,price);

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

    public void readFile(File file){
        //Try
        //If file is .tsb
        if(file.toString().endsWith(".tsb")){
            tsbReader(file);

        }
        //Call the TSB method
        //Else if the file is .html
        else if(file.toString().endsWith(".html")){
            //Call the htmlReader() method
            htmlReader(file);

        }
        else if(file.toString().endsWith(".json")){
            jsonReader(file);
        }
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

        }
        //Else if .html
        else if(filePath.endsWith(".html")){
            //Call .htmlWriter
            htmlWriter(file);

        }
        //Else .jsonWriter
        else if(filePath.endsWith(".json")) {
            jsonWriter(file);

        }


    }
    void tsbReader(File file){
        //Clear the current Inventory to make way for the new Inventory
        clearList();
        //Create scanner  for each line
        try {
            Scanner fileReader = new Scanner(file);
            Scanner dataReader = null;
            int index = 0;
            while(fileReader.hasNextLine()){
                dataReader = new Scanner(fileReader.nextLine());
                dataReader.useDelimiter("\t");
                InventoryItem item = new InventoryItem();
                while (dataReader.hasNext()){
                    String data = dataReader.next();
                    if(index == 0){
                        item.setSerialNumber(data);
                    }
                    else if(index == 1){
                        item.setName(data);
                    }
                    else if(index == 2){
                        item.setPrice(Double.parseDouble(data));
                    }
                    else{
                        System.out.println(data);
                    }
                    index++;
                }
                index = 0;
                addItem(item);
                dataReader.close();
        }
            fileReader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
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
    void htmlReader(File file){
        //Clear inventory
        clearList();
        String rawText;
        StringBuilder builder = new StringBuilder();

        //Create scanner to read each line
        try {
            Scanner reader = new Scanner(file);
            //Read file into string
            while(reader.hasNextLine()){
                builder.append(reader.nextLine());
            }
            rawText = builder.toString();
            //Parse the raw text
            Document doc = Jsoup.parse(rawText);
            //Get the rows without the HTML tags
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tr");
            for(int i = 1; i < rows.size(); i++) {
                Element r = rows.get(i);
                Elements c = r.select("td");
                InventoryItem item = new InventoryItem(c.get(0).text(),c.get(1).text(),Double.parseDouble(c.get(2).text()));
                addItem(item);
            }
            reader.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        //Use Jsoup to read html file

    }

    void jsonReader(File file){
        //Clear Inventory
        clearList();
        try {
            FileReader reader = new FileReader(file);
            Gson gson = new Gson();
            ArrayList<InventoryItem> jsonList = gson.fromJson(reader,new TypeToken<ArrayList<InventoryItem>>(){}.getType());
            for(InventoryItem item:jsonList){
                addItem(item);
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
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
            e.printStackTrace();
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
