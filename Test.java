public class Test {
    public static void main(String[] args) {
        /**
         * Ödev dokümanındaki örneğin gerçekleştirilmesi
         * İhtiyaç duyulan hash fonksiyonları aşağıda tanımlanmıştır.
         * Cuckoo.java içindeki ekleme metodunu yazdığınızda ekran
         * çıktıları ödev dokümanındaki gibi olmalıdır.
         */
        // Tablo boyutu
        int tableSize = 7;
        // Eklenecek elemanlar
        int[] eklenecek = new int[] { 8, 10, 2, 99, 3, 85, 47, 6, 71};
        // Hash 1
        DivisionHashFunction h1 = new DivisionHashFunction(tableSize);
        // Hash 2
        RMinusModRHashFunction h2 = new RMinusModRHashFunction(tableSize);
        // Tablo 1
        HashTable table1 = new HashTable(tableSize, h1, "password");
        // Tablo 2
        HashTable table2 = new HashTable(tableSize, h2, "password");
        // Cuckoo nesnesi, tabloları ve maks deneme sayısını içerir
        Cuckoo cuckoo = new Cuckoo(5, table1, table2);
        System.out.println("Başlangıç");
        // Tabloları yazdır
        System.out.println(cuckoo);
        // Değerleri ekleyecek döngü
        for (int deger : eklenecek) {
            // Değeri ekle
            boolean success = cuckoo.insert(deger);
            // Başarı durumunu yazdır
            System.out.println("Başarılı: " + success);
            // Tabloları yazdır
            System.out.println(cuckoo);
        }
        // Sonda sayılarını sıfırla
        cuckoo.resetProbeCounts("password");
        /**
         * Bu adımdan sonra birbirinden bağımsız olarak:
         * cuckoo.search(47) araması true dönecektir, sonda
         * sayıları ise 1,0 olacaktır
         * cuckoo.search(85) araması true dönecektir, sonda
         * sayıları 1,1 olacaktır
         * cuckoo.search(20) araması false dönecektir, sonda
         * sayıları 1,1 olacaktır
         */

    }
}

//-----------------------------Hash Fonksiyonları--------------------//
/**
 * Aşağıda kullanılan hash fonksiyonları bilmoodle testlerinde
 * kullanılanlar ile aynıdır. Bu projede sadece iki tanesi
 * kullanılmıştır. İsteyenler bu fonksiyonları test için kullanabilir.
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
//-----------------------------Hash Fonksiyonları--------------------//
