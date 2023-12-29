package com.cooba.task;

public interface Task {
    void execute();

    <T> T getCron();
}
