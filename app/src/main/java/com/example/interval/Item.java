package com.example.interval;

public class Item {

    String title;
    int set, run_sec, run_min, rest_sec, rest_min;

    public Item(String title, int set, int run_sec, int run_min, int rest_sec, int rest_min) {

        this.title = title;
        this.set = set;
        this.run_sec = run_sec;
        this.run_min = run_min;
        this.rest_sec = rest_sec;
        this.rest_min = rest_min;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getRun_sec() {
        return run_sec;
    }

    public void setRun_sec(int run_sec) {
        this.run_sec = run_sec;
    }

    public int getRun_min() {
        return run_min;
    }

    public void setRun_min(int run_min) {
        this.run_min = run_min;
    }

    public int getRest_sec() {
        return rest_sec;
    }

    public void setRest_sec(int rest_sec) {
        this.rest_sec = rest_sec;
    }

    public int getRest_min() {
        return rest_min;
    }

    public void setRest_min(int rest_min) {
        this.rest_min = rest_min;
    }
}