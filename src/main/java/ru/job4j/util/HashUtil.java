package ru.job4j.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Генерирует sha3-256 хэш без соли.
 * MessageDigest не потокобезопасный
 */
public class HashUtil {
    private static final Logger logger = LoggerFactory.getLogger(HashUtil.class);
    private final MessageDigest digest;

    private HashUtil() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA3-256");
    }

    public static HashUtil getInstance() {
        HashUtil result = null;
        try {
            result = new HashUtil();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Can't get MessageDigest instance", e);
        }
        return result;
    }

    public String getHash(String string) {
        final byte[] hashBytes = digest.digest(
                string.getBytes(StandardCharsets.UTF_8)
        );
        return bytesToHex(hashBytes);
    }

    private String bytesToHex(byte[] hash) {
        var hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            var hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
