package com.example.shade.model;

public class School {

    private String scCode;
    private String schoolCode;
    private String location;
    private String name;

    public School(String scCode, String schoolCode, String location, String name) {
        this.scCode = scCode;
        this.schoolCode = schoolCode;
        this.location = location;
        this.name = name;
    }

    public School(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getScCode() {
        return scCode;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }
}
