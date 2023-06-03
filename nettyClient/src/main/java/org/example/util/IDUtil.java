package org.example.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class IDUtil {
    public String randomId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
