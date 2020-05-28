package com.myapplicationdev.android.apii_p06_ps;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String task;
    private String desc;

    public Task(int id, String task, String desc) {
        this.id = id;
        this.task = task;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return id + ". " + task + "\n" + desc;
    }
}
