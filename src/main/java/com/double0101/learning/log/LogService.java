package com.double0101.learning.log;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;

    private boolean isShutDown;
    private int reservations;

    public LogService(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.writer = writer;
        isShutDown = false;
        reservations = 0;
        loggerThread = new LoggerThread();
    }

    public void start() { loggerThread.start(); }

    public void stop() {
        synchronized (this) { isShutDown = true; }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutDown) throw new IllegalStateException();
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while(true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutDown && reservations == 0)
                                break;
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) { --reservations; }
                        writer.println(msg);
                    } catch (InterruptedException e) { /* retry */ }
                } 
            } finally {
                writer.close();
            }
        }
    }
}