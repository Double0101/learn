package com.double0101.learning.buffer;

import com.double0101.learning.buffer.base.BaseBoundedBuffer;

public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private final long SLEEP_GRANULARITY = 100;
    
    public SleepyBoundedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }
}