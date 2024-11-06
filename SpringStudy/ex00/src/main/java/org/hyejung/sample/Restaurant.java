package org.hyejung.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Restaurant {

    private final Chef chef;

    @Autowired
    public Restaurant(Chef chef) {
        this.chef = chef;
    }
}
