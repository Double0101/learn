package com.double0101.learning.cache;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ComputeService<V> {
    private static final int DEFAULT_NTHREADS = 20;
    private final Executor exec;

    public ComputeService() {
        this(DEFAULT_NTHREADS);
    }

    public ComputeService(int nThreads) {
        exec = Executors.newFixedThreadPool(nThreads);
    }

    public void execute(FutureTask<V> task) {
        exec.execute(task);
    }
}