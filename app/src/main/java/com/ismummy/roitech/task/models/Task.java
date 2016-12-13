package com.ismummy.roitech.task.models;

import java.io.Serializable;

/**
 * Created by ISMUMMY on 10/11/2016.
 */

public class Task implements Serializable {
    private String title;
    private String date;
    private String id;
    private String start;
    private String description;

    public Task() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
