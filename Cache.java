import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<K, V> {
  private final int maxSize;
  private final Map<K, V> cache;
  private final Map<K, V> slowStore;

  public Cache(int maxSize, Map<K, V> slowStore) {
    // initialize cache with max size 
    this.maxSize = maxSize;

    // use a linked hashmap to keep order
    this.cache = new LinkedHashMap<K, V>(maxSize, .75f, true) {
      @Override
      protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // override to remove the eldest entry when the size exceeds max
        return size() > maxSize;
      }
    };
    this.slowStore = slowStore;
    // slowStore Cache should not be prefilled with the slow store
  }

  // retrieve values from the cache or the slow store
  public V fetch(K key) {
    // return null for null keys
    if (key == null) {
      return null;
    }
    // if the key is in the cache, return the value
    if (cache.containsKey(key)) {
      return cache.get(key);
    } else {
      // if the key is not in the cache, put it in the cache and return the value
      V value = slowStore.get(key); 
      if (value != null) {
        cache.put(key, value);
      }
      return value;
    }
  }

  // return the current size of the cache
  public int size() {
    return cache.size();
  }

  // check if the cache contains the specified key
  public boolean containsKey(K key) {
    return cache.containsKey(key);
  }
}
