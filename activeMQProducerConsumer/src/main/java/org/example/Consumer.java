package org.example;

public class Consumer {

    private static final String QUEUE_NAME = "queuename";

    public static void main(String[] args) throws Exception {
        SimpleQueue queue = new SimpleQueue(QUEUE_NAME);
        queue.receive();
        queue.close();
    }

}
