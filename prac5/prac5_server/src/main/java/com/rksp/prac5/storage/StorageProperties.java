package com.rksp.prac5.storage;

import org.springframework.stereotype.Component;

@Component("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac5/prac5_server/src/main/resources/uploaded";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
