package fr.skytorstd.doxerbot.states;

public enum QueueAfterTimes {
    ERROR_TIME(15),
    SUCCESS_TIME(30);

    private long queueAfterTime;

    QueueAfterTimes(long queueAfterTime) {
        this.queueAfterTime = queueAfterTime;
    }

    public long getQueueAfterTime() {
        return queueAfterTime;
    }
}