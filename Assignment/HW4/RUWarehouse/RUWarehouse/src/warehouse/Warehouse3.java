package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Ishaan Ivaturi
 */
public class Warehouse {
    private Sector[] sectors;

    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }

    /**
     * Provided method, code the parts to add their behavior
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);

    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
        Product newproduct = new Product(id, name, stock, day, demand);
        sectors[id%10].add(newproduct);

    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * 
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        // IMPLEMENT THIS METHOD
        int i = sectors[id%10].getSize();
        if(sectors[id%10] != null)
        {
            sectors[id%10].swim(i);
        }

    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5
     * while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the
     * Sector class
     * 
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        // IMPLEMENT THIS METHOD
        Sector sector = sectors[id%10];

        if (sector.getSize() == 5) {
            sector.swap(1, 5);
            sector.deleteLast();
            sector.sink(1);
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * 
     * @param id     The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        // IMPLEMENT THIS METHOD
        Sector sector = sectors[id%10];

        for (int i = 1; i < sector.getSize(); ++i) 
        {
            if (sector.get(i).getId() == id) 
            {
                sector.get(i).updateStock(amount);
                return;
            }

        }
    }

    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(),
     * .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * 
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        // IMPLEMENT THIS METHOD
        Sector sector = sectors[id%10];

        for (int i = 1; i < sector.getSize(); ++i) 
        {
            if (sector.get(i).getId() == id) 
            {
                sector.swap(i, sector.getSize());
                sector.deleteLast();
                sector.sink(i);
                if (i <= sector.getSize()) 
                {
                    sector.swim(i);
                }
            }
        }
        return;
    }

    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector
     * class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(),
     * updateStock(), updateDemand() methods
     * 
     * @param id     The id of the purchased product
     * @param day    The currentent day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        // IMPLEMENT THIS METHOD
        Sector sector = sectors[id%10];

        for (int i = 1; i <= sector.getSize(); ++i) 
        {
            if(sector.get(i) == null)
            {
                continue;
            }
            if (sector.get(i).getId() == id)
            {
                if(sector.get(i).getStock()  >= amount) 
                {
                    sector.get(i).setLastPurchaseDay(day);
                    sector.get(i).updateStock(-amount);
                    sector.get(i).updateDemand(amount);
                    sector.sink(i);
                }
                break;
            }
        }
    }

    /*
     * Product currententProduct = sectors[id%10].get(i);
     * if (currententProduct.getId() == id) {
     * if (currententProduct.getStock() >= amount) {
     * currententProduct.setStock(currententProduct.getStock() - amount);
     * } else {
     * sectors[id%10].get(i).setLastPurchaseDay(day);
     * sectors[id%10].get(i).updateStock(-amount);
     * sectors[id%10].get(i).updateDemand(amount);
     * sectors[id%10].sink(i);
     * }
     * 
     * }
     */

    /**
     * Construct a better scheme to add a product, where empty spaces are always
     * filled
     * 
     * @param id     The id of the item to add
     * @param name   The name of the item to add
     * @param stock  The stock of the item to add
     * @param day    The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        // IMPLEMENT THIS METHOD
        int lastID = id;
        if (id >= 10) 
        {
            lastID = id % 10;
        }
        Sector sector = sectors[lastID];
        if (sector.getSize() != 5) 
        {
            addToEnd(id, name, stock, day, demand);
            fixHeap(id);
        } else {
            int current = (lastID + 1) % 10;

            while (current != lastID) 
            {
                if (sectors[current].getSize() != 5) 
                {
                    Product p = new Product(id, name, stock, day, demand);
                    sectors[current].add(p);
                    sectors[current].swim(sectors[current].getSize());
                    return;
                }
                current = (current + 1) % 10;
            }
            addProduct(id, name, stock, day, demand);
        }
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }

        return warehouseString + "]";
    }

    /*
     * Do not remove this method, it is used by Autolab
     */
    public Sector[] getSectors() {
        return sectors;
    }
}
