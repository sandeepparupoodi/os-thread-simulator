package com.ossim.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Semaphore {
    private final String name;
    private final int initialValue;
    private int value;
    private String heldBy;
    private final Queue<String> waitQueue;

    public Semaphore(String name, int initialValue) {
        this.name = name;
        this.initialValue = Math.max(1, initialValue);
        this.value = this.initialValue;
        this.heldBy = null;
        this.waitQueue = new ArrayDeque<>();
    }

    public boolean tryAcquire(String tid) {
        if (value > 0) {
            value--;
            heldBy = tid;
            return true;
        }
        if (!waitQueue.contains(tid)) {
             waitQueue.add(tid);
}
        return false;
    }

    public String release() {
        heldBy = null;
        String next = waitQueue.poll();
        if (next == null) value++;
        else heldBy = next;
        return next; // returns waiting tid that now gets it, or null
    }

    public String getName() { return name; }
    public int getValue() { return value; }
    public String getHeldBy() { return heldBy; }
    public int getWaitingCount() { return waitQueue.size(); }
    public boolean isLocked() { return value <= 0; }
}
