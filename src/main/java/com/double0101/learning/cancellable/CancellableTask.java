package com.double0101.learning.cancellable;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

public interface CancellableTask<T> extends Cancellable, Callable<T> {
    RunnableFuture<T> newTask();
}