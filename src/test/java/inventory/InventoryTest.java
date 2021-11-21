package inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory testInventory;
    InventoryItem item1 = new InventoryItem("j-0c3-ow2-4dx","Gatorade",2.30);
    InventoryItem item2 = new InventoryItem("h-0c5-ow3-4dx","Sprite",2.00);

    @BeforeEach
    void testItemInit(){
        testInventory = new Inventory();
    }
    @Test
    void createInventoryItem() {
        InventoryItem test = testInventory.createInventoryItem("j-0c3-ow2-4dx","Gatorade",2.30);
        assertEquals("j-0c3-ow2-4dx",test.getSerialNumber());
        assertEquals("Gatorade",test.getName());
        assertEquals(2.30,test.getPrice());
    }

    @Test
    void addItem() {
        testInventory.addItem(item1);
        assertEquals("j-0c3-ow2-4dx",testInventory.getInventoryArray().get(0).getSerialNumber());
        assertEquals("Gatorade",testInventory.getInventoryArray().get(0).getName());
        assertEquals(2.30,testInventory.getInventoryArray().get(0).getPrice());
    }

    @Test
    void removeItem() {
        testInventory.addItem(item1);
        testInventory.removeItem(item1);
        assertEquals("[]",testInventory.getInventoryArray().toString());
        assertEquals("{}",testInventory.getInventoryMap().toString());

        testInventory.addItem(item1);
        testInventory.addItem(item2);
        testInventory.removeItem(item1);

        assertEquals("["+item2+"]",testInventory.getInventoryArray().toString());
        assertEquals("{"+item2.getSerialNumber()+"="+item2+"}",testInventory.getInventoryMap().toString());
    }

    @Test
    void clearList() {
        testInventory.addItem(item1);
        testInventory.addItem(item2);
        testInventory.clearList();
        assertEquals("[]",testInventory.getInventoryArray().toString());
        assertEquals("{}",testInventory.getInventoryMap().toString());
    }

    @Test
    void editItemName() {
        testInventory.addItem(item1);
        testInventory.editItemName("Blue Gatorade", item1.getSerialNumber());
        assertEquals("Blue Gatorade",testInventory.getInventoryMap().get(item1.getSerialNumber()).getName());
        assertEquals("Blue Gatorade",testInventory.getInventoryArray().get(testInventory.getInventoryArray().indexOf(item1)).getName());
    }

    @Test
    void editSerialNumber() {
        testInventory.addItem(item1);
        testInventory.editSerialNumber("h-0c5-ow3-4dx",item1);
        int itemIndex = testInventory.getInventoryArray().indexOf(item1);
        assertEquals("h-0c5-ow3-4dx",testInventory.getInventoryArray().get(itemIndex).getSerialNumber());
        assertEquals("h-0c5-ow3-4dx",testInventory.getInventoryMap().get(item1.getSerialNumber()).getSerialNumber());
    }

    @Test
    void isValidSerial(){
        String serial1 = "h-0c5-ow3-4dx";
        String serial2 = "2-0c5-ow3-4dx";
        String serial3 = "c-0c5+ow3-4dx";
        String serial4 = "c-0c5-ofw3-4dx";
        String serial5 = "c-05-ow3-4dx";
        String serial6 = "%-0c5-ow3-4dx";
        assertEquals(true,testInventory.isValidSerial(serial1));
        assertEquals(false,testInventory.isValidSerial(serial2));
        assertEquals(false,testInventory.isValidSerial(serial3));
        assertEquals(false,testInventory.isValidSerial(serial4));
        assertEquals(false,testInventory.isValidSerial(serial5));
        assertEquals(false,testInventory.isValidSerial(serial6));
    }

    @Test
    void isSerialSame(){
        testInventory.addItem(item1);
        testInventory.addItem(item2);
        assertEquals(true,testInventory.isSerialSame("h-0c5-ow3-4dx"));
        assertEquals(false,testInventory.isSerialSame("x-0c5-ow3-4dx"));

    }

    @Test
    void readFile() {
        testInventory.addItem(item1);
        testInventory.addItem(item2);
        Inventory readerInventory = new Inventory();
        File test1 = new File(".\\src\\test\\java\\inventory\\InventoryListTest1.tsb");
        File test2 = new File(".\\src\\test\\java\\inventory\\InventoryListTest2.html");
        File test3 = new File(".\\src\\test\\java\\inventory\\InventoryListTest3.json");
        readerInventory.readFile(test1);
        assertEquals(true,readerInventory.getInventoryArray().get(0).getSerialNumber().equals(testInventory.getInventoryArray().get(0).getSerialNumber()));
        readerInventory.readFile(test2);
        assertEquals(true,readerInventory.getInventoryArray().get(0).getSerialNumber().equals(testInventory.getInventoryArray().get(0).getSerialNumber()));
        readerInventory.readFile(test3);
        assertEquals(true,readerInventory.getInventoryArray().get(0).getSerialNumber().equals(testInventory.getInventoryArray().get(0).getSerialNumber()));
    }

    @Test
    void writeFile(@TempDir Path temp) {

    }

}