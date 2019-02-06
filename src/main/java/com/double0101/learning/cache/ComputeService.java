package com.double0101.learning.cache;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ComputeService<V> extends ThreadPoolExecutor {
    private static final int DEFAULT_NTHREADS = 20;

    public ComputeService() {
        this(DEFAULT_NTHREADS);
    }

    public ComputeService(int nThreads) {
        super(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
              new LinkedBlockingQueue<Runnable>());
    }
}