package com.example.shade;

public class School {

    private final String scCode;
    private final String schoolCode;
    private final String location;
    private final String name;

    public School(String scCode, String schoolCode, String location, String name) {
        this.scCode = scCode;
        this.schoolCode = schoolCode;
        this.location = location;
        this.name = name;
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
