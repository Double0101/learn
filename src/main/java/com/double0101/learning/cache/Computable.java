package com.double0101.learning.cache;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}