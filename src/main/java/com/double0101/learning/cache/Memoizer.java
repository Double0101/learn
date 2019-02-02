package com.double0101.learning.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class Memoizer<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A, V> c;
    private final ComputeService<V> service;

    public Memoizer(Computable<A, V> c) {
        service = new ComputeService<V>();
        this.c = c;
    }
    
    public Memoizer(Computable<A, V> c, int nThreads) {
        service = new ComputeService<V>(nThreads);
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null) {
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = cache.putIfAbsent(arg, ft);
            if (f == null) {
                f = ft;
                service.execute(ft);
            }
        }
        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launcherThrowable(e.getCause());
        }
    }

    public static RuntimeException launcherThrowable(Throwable t) {
        if (t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("Not unchecked", t);
    }
}