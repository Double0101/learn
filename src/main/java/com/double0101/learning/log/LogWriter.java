package com.double0101.learning.log;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    public LogWriter(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.logger = new LoggerThread(writer);
    }

    public void start() { logger.start(); }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void run() {
            try {
                while(true) {
                    writer.println(queue.take());
                } 
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}