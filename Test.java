public class Test {
    public static void main(String[] args) {
        /**
         * Implementation of the example in the assignment document
         * Required hash functions are defined below.
         * When you implement the insert method in Cuckoo.java,
         * the output on the screen should be as in the assignment document.
         */
        // Table size
        int tableSize = 7;
        // Elements to be added
        int[] toBeAdded = new int[] { 8, 10, 2, 99, 3, 85, 47, 6, 71};
        // Hash 1
        DivisionHashFunction h1 = new DivisionHashFunction(tableSize);
        // Hash 2
        RMinusModRHashFunction h2 = new RMinusModRHashFunction(tableSize);
        // Table 1
        HashTable table1 = new HashTable(tableSize, h1, "password");
        // Table 2
        HashTable table2 = new HashTable(tableSize, h2, "password");
        // Cuckoo object containing tables and maximum attempt count
        Cuckoo cuckoo = new Cuckoo(5, table1, table2);
        System.out.println("Start");
        // Print the tables
        System.out.println(cuckoo);
        // Loop to add values
        for (int value : toBeAdded) {
            // Add the value
            boolean success = cuckoo.insert(value);
            // Print the success status
            System.out.println("Successful: " + success);
            // Print the tables
            System.out.println(cuckoo);
        }
        // Reset probe counts
        cuckoo.resetProbeCounts("password");
        /**
         * After this step, independently:
         * cuckoo.search(47) will return true, probe counts will be 1, 0
         * cuckoo.search(85) will return true, probe counts will be 1, 1
         * cuckoo.search(20) will return false, probe counts will be 1, 1
         */
    }
}

//-----------------------------Hash Functions--------------------//
/**
 * The hash functions used below are the same as those used in the moodle tests.
 * Only two of them are used in this project. Those who wish can use these functions for testing.
 */
class FibonacciHashFunction implements HashFunction {
    private int tableSize;

    public FibonacciHashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public int getHash(int value) {
        long hash = value * 2654435761L;
        return (int) (hash % tableSize);
    }
}

class BitwiseXORHashFunction implements HashFunction {
    private int tableSize;

    public BitwiseXORHashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public int getHash(int value) {
        return (value ^ (value >>> 4)) % tableSize;
    }
}

class DivisionHashFunction implements HashFunction {
    private int tableSize;

    public DivisionHashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public int getHash(int value) {
        return value % tableSize;
    }
}

class RMinusModRHashFunction implements HashFunction {
    private int primeR;

    public RMinusModRHashFunction(int tableSize) {
        this.primeR = findLargestPrime(tableSize);
    }

    @Override
    public int getHash(int value) {
        return primeR - (value % primeR);
    }

    private int findLargestPrime(int n) {
        for (int i = n - 1; i > 1; i--) {
            if (isPrime(i)) {
                return i;
            }
        }
        return 2;
    }

    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}

class MidSquareHashFunction implements HashFunction {
    private int tableSize;

    public MidSquareHashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    @Override
    public int getHash(int value) {
        int squared = value * value;
        int hash = squared % 1000;
        return hash % tableSize;
    }
}
//-----------------------------Hash Functions--------------------//
