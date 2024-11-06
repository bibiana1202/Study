package org.hyejung.sample;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class Chef {
    private String name = "Master Chef";

    public void cook() {
        System.out.println(name + " is cooking a delicious meal!");
    }
}
