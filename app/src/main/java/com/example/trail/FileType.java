package com.example.trail;

import java.io.IOException;

public enum FileType {
    FILE_TYPE_JPG, JPG, PNG;

    public String toValue() {
        switch (this) {
            case FILE_TYPE_JPG: return "jpg";
            case JPG: return "JPG";
            case PNG: return "png";
        }
        return null;
    }

    public static FileType forValue(String value) throws IOException {
        if (value.equals("jpg")) return FILE_TYPE_JPG;
        if (value.equals("JPG")) return JPG;
        if (value.equals("png")) return PNG;
        throw new IOException("Cannot deserialize FileType");
    }
}