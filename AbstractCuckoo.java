import java.util.Arrays;
import java.util.List;

/**
 * HashFunction Interface
 */
interface HashFunction {
    public int getHash(int value);
}

/**
 * HashTable Class
 */
class HashTable {
    // Table size
    private int tableSize;
    // Summary table
    private Integer[] table;
    // Probe count
    private int probeCount;
    // Hash function
    private HashFunction hashFunction;
    // Protection against reset operation
    private String resetPass;
    // Constructor
    public HashTable(int tableSize, HashFunction hashFunction, String resetPass) {
        this.tableSize = tableSize;
        this.table = new Integer[tableSize];
        this.hashFunction = hashFunction;
        this.probeCount = 0;
        this.resetPass = resetPass;
    }
    /**
     * Calls the hash function of the table
     * @param key Value
     * @return Hash value
     */
    public int hash(int key) {
        return hashFunction.getHash(key);
    }

    /**
     * Returns the probe count in the table
     * @return Probe count
     */
    public int getProbeCount() {
        return probeCount;
    }

    /**
     * Resets the probe count, students are not expected to call this method.
     * @param pass
     */
    public void resetProbeCount(String pass) {
        if (resetPass.equals(pass)) {
            probeCount = 0;
        }
    }

    /**
     * Reads a value from the table and increments the probe count
     * @param i Position to read
     * @return Value at position i
     */
    public Integer get(int i) {
        if (i >= tableSize) {
            throw new ArrayIndexOutOfBoundsException(
                    "Table size exceeded. Table size: " + tableSize + " Accessed index: " + i + "\n");
        }
        probeCount++;
        return table[i];
    }

    /**
     * Sets a value at a position in the table
     * @param i Position
     * @param key Value to be assigned
     */
    public void set(int i, int key) {
        if (i >= tableSize) {
            throw new ArrayIndexOutOfBoundsException(
                    "Table size exceeded. Table size: " + tableSize + " Accessed index: " + i + "\n");
        }
        table[i] = key;
    }

    /**
     * Returns the array in the table as a list, for informational purposes
     * @return List of elements
     */
    public List<Integer> getTableAsList() {
        return Arrays.asList(table);
    }

    /**
     * Returns the type of the Hash class, for informational purposes
     * @return
     */
    public Class getHashClass() {
        return hashFunction.getClass();
    }
}

/**
 * AbstractCuckoo Class
 */
public abstract class AbstractCuckoo {
    // Maximum probe count to use during insertion
    protected final int MAX_REHASH_ATTEMPTS;
    // Table 1
    protected HashTable table1;
    // Table 2
    protected HashTable table2;
    /**
     * Constructor
     * @param max_rehash_attempts Maximum probe count
     * @param table1 Table 1
     * @param table2 Table 2
     */
    public AbstractCuckoo(int max_rehash_attempts, HashTable table1, HashTable table2) {
        MAX_REHASH_ATTEMPTS = max_rehash_attempts;
        this.table1 = table1;
        this.table2 = table2;
    }

    /**
     * Used to reset probe counts, not expected for students to use
     * @param pass
     */
    public void resetProbeCounts(String pass) {
        table1.resetProbeCount(pass);
        table2.resetProbeCount(pass);
    }

    /**
     * Method to convert the Cuckoo table to a string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Integer> t1 = table1.getTableAsList();
        List<Integer> t2 = table2.getTableAsList();
        sb.append("|------------|\n");
        sb.append(String.format("|%2s|%4s|%4s|\n", "i", "t1", "t2"));
        sb.append("|--|----|----|\n");
        for (int i = 0; i < t1.size(); i++) {
            sb.append(String.format("|%2d|%4d|%4d|\n",
                    i,
                    t1.get(i),
                    t2.get(i)));
        }
        sb.append("|--|----|----|\n");
        sb.append(String.format("|PC|%4s|%4s|\n",
                table1.getProbeCount(),
                table2.getProbeCount()));
        sb.append("|------------|\n");
        sb.append("H1: " + table1.getHashClass().getName() + "\n");
        sb.append("H2: " + table2.getHashClass().getName() + "\n");
        sb.append("MAX_REHASH_ATTEMPTS: " + MAX_REHASH_ATTEMPTS + "\n");
        return sb.toString().replace(' ', ' ');
    }

    // Insertion method
    public abstract boolean insert(int key);
    // Search method
    public abstract boolean search(int key);
}
