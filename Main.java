import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
  public static void main(String[] args) {
    AtomicInteger accesses = new AtomicInteger();
    Map<String, Integer> store = new HashMap<>() {
      {
        put("Tiger", 63);
        put("Aardvark", 19);
        put("Elephant", 32);
        put("Rhinoceros", 99);
        put("Dog", 3);
        put("Giraffe", 65);
        put("Zebra", 45);
        put("Crocodile", 23);
        put("Alligator", 75);
        put("Chimpanzee", 15);
      }

      @Override
      public Set<Entry<String, Integer>> entrySet() {
        accesses.addAndGet(super.size());
        return super.entrySet();
      }

      @Override
      public Integer get(Object key) {
        accesses.incrementAndGet();
        return super.get(key);
      }

      @Override
      public boolean containsKey(Object key) {
        accesses.incrementAndGet();
        return super.containsKey(key);
      }
    };

    Cache<String, Integer> cache = new Cache<>(5, store);
    check(cache.size() == 0, "size should be 0, not " + cache.size());
    check(cache.fetch("Rhinoceros") == 99, "Rhinoceros should be 99");
    check(cache.fetch("Zebra") == 45, "Zebra should be 45");
    check(cache.fetch("Chimpanzee") == 15, "Chimpanzee should be 15");
    check(cache.fetch(null) == null, "null should be null");
    check(cache.fetch("Antelope") == null, "Antelope should be null");
    check(cache.fetch("Antelope") == null, "Antelope should be null");
    check(cache.fetch("Antelope") == null, "Antelope should be null");
    check(cache.size() == 5, "size should be 5, not " + cache.size());
    check(accesses.intValue() == 5, "store should have been queried 5 times, not " + accesses.intValue());
    check(cache.fetch("Rhinoceros") == 99, "Rhinoceros should be 99");
    check(cache.fetch("Rhinoceros") == 99, "Rhinoceros should be 99");
    check(cache.fetch("Rhinoceros") == 99, "Rhinoceros should be 99");
    check(cache.containsKey("Zebra"), "cache should contain Zebra");
    check(cache.containsKey("Chimpanzee"), "cache should contain Chimpanzee");
    check(cache.containsKey(null), "cache should contain null");
    check(cache.containsKey("Antelope"), "cache should contain Antelope");
    check(cache.fetch("Rhinoceros") == 99, "Rhinoceros should be 99");
    check(cache.fetch("Crocodile") == 23, "Crocodile should be 23");
    check(cache.size() == 5, "size should be 5, not " + cache.size());
    check(cache.fetch("Chimpanzee") == 15, "Chimpanzee should be 15");
    check(cache.fetch(null) == null, "null should be null");
    check(cache.fetch("Tiger") == 63, "Tiger should be 63");
    check(cache.fetch("Dog") == 3, "Dog should be 3");
    check(cache.fetch("Crocodile") == 23, "Crocodile should be 23");
    check(cache.fetch("Chimpanzee") == 15, "Chimpanzee should be 15");
    check(cache.fetch("Giraffe") == 65, "Giraffe should be 65");
    check(cache.fetch("Elephant") == 32, "Elephant should be 32");
    check(cache.size() == 5, "size should be 5, not " + cache.size());
    check(cache.containsKey("Dog"), "cache should contain Dog");
    check(cache.containsKey("Chimpanzee"), "cache should contain Chimpanzee");
    check(cache.containsKey("Giraffe"), "cache should contain Giraffe");
    check(cache.containsKey("Elephant"), "cache should contain Elephant");
    check(cache.containsKey("Crocodile"), "cache should contain Crocodile");
    check(accesses.intValue() == 10, "store should have been queried 10 times, not " + accesses.intValue());
    System.out.println("PASSED!");
  }

  private static void check(boolean b, String message) {
    if (!b) {
      throw new AssertionError(message);
    }
  }
}
