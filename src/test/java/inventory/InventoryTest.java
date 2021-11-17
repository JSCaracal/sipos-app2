package inventory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    }

    @Test
    void editItemName() {
    }

    @Test
    void editSerialNumber() {
    }

    @Test
    void readFile() {
    }

    @Test
    void writeFile() {
    }

    @Test
    void tsbReader() {
    }

    @Test
    void htmlReader() {
    }

    @Test
    void jsonReader() {
    }

    @Test
    void tsbWriter() {
    }

    @Test
    void htmlWriter() {
    }

    @Test
    void jsonWriter() {
    }
}