package org.example;

public class Producer {

    private static final String QUEUE_NAME = "queuename";

    public static void main(String[] args) throws Exception {
        SimpleQueue queue = new SimpleQueue(QUEUE_NAME);
        queue.send("Welcome");
        queue.close();
    }
}
