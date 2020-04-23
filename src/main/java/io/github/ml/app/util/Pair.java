package io.github.ml.app.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nd4j.base.Preconditions;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This is a neat way I borrowed from the stanford-nlp lib of handling data that is related in a key-value
 * <p>
 * sort of way of just without resorting to using an array. That way we can set custom rules about the pair
 *
 * @param <K>
 * @param <V>
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Pair<K, V> implements Serializable {
    private static final long serialVersionUID = -8231970819309329256L;
    protected K key;
    protected V value;

    @Override
    public String toString() {
        return "Pair{" +
            "key=" + (key instanceof int[] ? Arrays.toString((int[]) key) : key) +
            ", value=" + (value instanceof int[] ? Arrays.toString((int[]) value) : value) +
            '}';
    }

    public K getLeft() {
        return key;
    }

    public V getRight() {
        return value;
    }

    public K getFirst() {
        return key;
    }

    public V getSecond() {
        return value;
    }

    public void setFirst(K first) {
        key = first;
    }

    public void setSecond(V second) {
        value = second;
    }

    public static <T, E> Pair<T, E> of(T key, E value) {
        return new Pair<T, E>(key, value);
    }

    public static <T, E> Pair<T, E> makePair(T key, E value) {
        return new Pair<T, E>(key, value);
    }

    public static <T, E> Pair<T, E> create(T key, E value) {
        return new Pair<T, E>(key, value);
    }

    public static <T, E> Pair<T, E> pairOf(T key, E value) {
        return new Pair<T, E>(key, value);
    }

    public static <T> Pair<T, T> fromArray(T[] arr) {
        Preconditions.checkArgument(arr.length == 2,
            "Can only create a pair from an array with two values, got %s", arr.length);
        return new Pair<>(arr[0], arr[1]);
    }
}

