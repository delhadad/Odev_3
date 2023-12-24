import java.util.Arrays;
import java.util.List;

/**
 * HashFunction Arayüzü
 */
interface HashFunction {
    public int getHash(int value);
}

/**
 * HashTable Sınıfı
 */
class HashTable {
    // Tablo boyutu
    private int tableSize;
    // Özet tablosu
    private Integer[] table;
    // Sonda sayısı
    private int probeCount;
    // Özet fonksiyonu
    private HashFunction hashFunction;
    // Sıfırlama işlemine karşı koruma
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
     * Tablonun hash fonksiyonunu çağırır
     * @param key Değer
     * @return Hash değeri
     */
    public int hash(int key) {
        return hashFunction.getHash(key);
    }

    /**
     * Tablodaki sonda sayısını verir
     * @return Sonda sayısı
     */
    public int getProbeCount() {
        return probeCount;
    }

    /**
     * Sonda sayısını sıfırlar, öğrencilerin bu metodu çağırması beklenmemektedir.
     * @param pass
     */
    public void resetProbeCount(String pass) {
        if (resetPass.equals(pass)) {
            probeCount = 0;
        }
    }

    /**
     * Tablodan bir değer okur ve sonda sayısını artırır
     * @param i Okunacak konum
     * @return i konumundaki değer
     */
    public Integer get(int i) {
        if (i >= tableSize) {
            throw new ArrayIndexOutOfBoundsException(
                    "Özet tablosunun boyutu aşılmış. Tablo boyutu: " + tableSize + " Erişilen indeks: " + i + "\n");
        }
        probeCount++;
        return table[i];
    }

    /**
     * Tablo içindeki bir konuma değer atar
     * @param i Konum
     * @param key Atanacak değer
     */
    public void set(int i, int key) {
        if (i >= tableSize) {
            throw new ArrayIndexOutOfBoundsException(
                    "Özet tablosunun boyutu aşılmış. Tablo boyutu: " + tableSize + " Erişilen indeks: " + i + "\n");
        }
        table[i] = key;
    }

    /**
     * Tablo içindeki diziyi liste olarak verir, bilgi amaçlıdır
     * @return Elemanların listesi
     */
    public List<Integer> getTableAsList() {
        return Arrays.asList(table);
    }

    /**
     * Hash sınıfının türünü döndürür, bilgi amaçlıdır
     * @return
     */
    public Class getHashClass() {
        return hashFunction.getClass();
    }
}

/**
 * AbstractCuckoo Sınıfı
 */
public abstract class AbstractCuckoo {
    // Ekleme yaparken kullanılacak maksimum sonda sayısı
    protected final int MAX_REHASH_ATTEMPTS;
    // Tablo 1
    protected HashTable table1;
    // Tablo 2
    protected HashTable table2;
    /**
     * Constructor
     * @param max_rehash_attempts Maksimum sonda sayısı
     * @param table1 Tablo 1
     * @param table2 Tablo 2
     */
    public AbstractCuckoo(int max_rehash_attempts, HashTable table1, HashTable table2) {
        MAX_REHASH_ATTEMPTS = max_rehash_attempts;
        this.table1 = table1;
        this.table2 = table2;
    }

    /**
     * Sonda sayılarını sıfırlamak için kullanılır, öğrencilerin kullanması beklenmez
     * @param pass
     */
    public void resetProbeCounts(String pass) {
        table1.resetProbeCount(pass);
        table2.resetProbeCount(pass);
    }

    /**
     * Cuckoo tablosunu metne dönüştüren metot
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

    // Ekleme metodu
    public abstract boolean insert(int key);
    // Arama metodu
    public abstract boolean search(int key);
}
