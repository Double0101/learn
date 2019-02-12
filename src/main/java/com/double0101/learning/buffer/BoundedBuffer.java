package com.double0101.learning.buffer;

import com.double0101.learning.buffer.base.BaseBoundedBuffer;

public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {
    public BoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }
}