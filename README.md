# Cache Implementation

This project provides a simple cache implementation in Java using a `Cache` class. The cache is designed to store data and provide efficient retrieval, avoiding slow data access from a "slow store" as much as possible. The "slow store" represents a slow data source that you want to minimize access to.

## Cache Implementation

The `Cache` class is responsible for managing the cache. It ensures that the cache:

- Does not exceed the specified maximum size.
- Does not pre-fill with data from the slow store.
- Uses an eviction strategy to remove the oldest entry based on access.

### How to Use the Cache

1. Import the `Cache` class into your project.
2. Create an instance of the `Cache` class, specifying the maximum cache size and a `Map` for the slow store.
3. Use the `fetch` method to retrieve values from the cache. If the value is not in the cache, it will be fetched from the slow store.

```java
Cache<String, Integer> cache = new Cache<>(5, slowStore);
Integer value = cache.fetch("key");
```

4. You can also check the cache size and if it contains a specific key using the `size` and `containsKey` methods.

```java
int size = cache.size();
boolean containsKey = cache.containsKey("key");
```

### Requirements

- The cache should not exceed the max size allocated.
- The cache should not be prefilled with the slow store.
- The eviction strategy should evict the oldest entry based on access.

**Note**: The assertions in the provided `Main` class test the implementation of the `Cache` class.

## Main Class

The `Main` class serves as a test suite for the `Cache` class. It demonstrates the behavior of the cache and validates that it meets the specified requirements.

