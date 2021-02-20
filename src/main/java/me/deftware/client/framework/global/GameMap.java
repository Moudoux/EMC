package me.deftware.client.framework.global;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Deftware
 */
@SuppressWarnings("unchecked")
public class GameMap {

    public static final GameMap INSTANCE = new GameMap();

    private final ConcurrentHashMap<GameCategory, ConcurrentHashMap<String, Object>> keys = new ConcurrentHashMap<>();

    private GameMap() {
       reset();
    }

    public int size() {
        return keys.values().stream()
                .mapToInt(ConcurrentHashMap::size)
                .sum();
    }

    public void reset() {
        keys.clear();
        for (GameCategory category : GameCategory.values())
            keys.put(category, new ConcurrentHashMap<>());
    }

    public boolean contains(IGameKey key) {
        return contains(key.getCategory(), key.getKey());
    }

    public boolean contains(GameCategory category, String key) {
        return keys.get(category).containsKey(key);
    }

    public <T> T put(IGameKey key, T value) {
        return put(key.getCategory(), key.getKey(), value);
    }

    public <T> T put(GameCategory category, String key, T value) {
        return (T) keys.get(category).put(key, value);
    }

    public <T> T get(IGameKey key, T value) {
        return get(key.getCategory(), key.getKey(), value);
    }

    public <T> T get(GameCategory category, String key, T value) {
        return (T) keys.get(category).getOrDefault(key, value);
    }

    public <T> T remove(IGameKey key) {
        return remove(key.getCategory(), key.getKey());
    }

    public <T> T remove(GameCategory category, String key) {
        return (T) keys.get(category).remove(key);
    }

}
