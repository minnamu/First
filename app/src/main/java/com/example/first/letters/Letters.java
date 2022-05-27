package com.example.first.letters;

public class Letters {
    private String name;
    private int imageId;
    private String count;

    public Letters(String name, int imageId, String count) {
        this.name = name;
        this.imageId = imageId;
        this.count = count;

    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    public String getCount() {
        return count;
    }
}
