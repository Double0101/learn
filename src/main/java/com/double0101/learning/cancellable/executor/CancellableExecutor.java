package com.double0101.learning.cancellable.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.double0101.learning.cancellable.CancellableTask;

public class CancellableExecutor extends ThreadPoolExecutor {

    public CancellableExecutor(int nThreads) {
        super(nThreads, nThreads, 0L,
              TimeUnit.MILLISECONDS, 
              new LinkedBlockingQueue<Runnable>());
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask) {
            return ((CancellableTask<T>) callable).newTask();
        }
        return super.newTaskFor(callable);
    }
}