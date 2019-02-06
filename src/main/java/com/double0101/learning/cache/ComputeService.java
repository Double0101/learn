package com.double0101.learning.cache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ComputeService<V> {
    private static final int DEFAULT_NTHREADS = 20;
    private final ExecutorService executor;

    public ComputeService() {
        this(DEFAULT_NTHREADS);
    }

    public ComputeService(int nThreads) {
        executor = Executors.newFixedThreadPool(nThreads);
    }

    public Future<V> submit(Runnable task) {
        return (Future<V>) executor.submit(task);
    }
}