package com.konstantinov.springshop.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    private String location = "src/main/resources/static/images/avatar";

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

}
