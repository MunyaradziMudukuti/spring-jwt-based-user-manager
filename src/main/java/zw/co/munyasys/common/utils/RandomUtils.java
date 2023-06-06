package zw.co.munyasys.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtils {

    public static String generatePassword() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 8);
    }

    public static String uuidGenerator() {
        return String.valueOf(System.nanoTime());
    }


    public static String generateToken() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 6)
                .toUpperCase();
    }

}
