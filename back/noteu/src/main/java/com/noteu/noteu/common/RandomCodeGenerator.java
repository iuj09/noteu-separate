package com.noteu.noteu.common;

import java.util.UUID;

public class RandomCodeGenerator {

    public static String generateRandomCode() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();

        String code = uuidStr.substring(0, 10);

        return code;
    }
}
