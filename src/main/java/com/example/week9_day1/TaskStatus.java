package com.example.week9_day1;

public enum TaskStatus {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    CANCELLED("Cancelled");

    private final String taskStatusTitle;

    TaskStatus(String taskStatusTitle) {
        this.taskStatusTitle = taskStatusTitle;
    }

    public boolean canTransitionTo(TaskStatus next) {
        if ((next == null) || (this == next)) {
            return false;
        }
        return switch (this) {
            case NEW -> (next == IN_PROGRESS || next == CANCELLED);
            case IN_PROGRESS -> next == DONE || next == CANCELLED;
            case DONE -> next == IN_PROGRESS || next == CANCELLED;
            case CANCELLED -> false;
        };
    }

    public String getTaskStatusTitle() {
        return taskStatusTitle;
    }
}
