package monty.task;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public String getDescription() {
        return description;
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
