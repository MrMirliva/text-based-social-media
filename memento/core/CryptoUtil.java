package memento.core;

import java.util.Base64;

final class CryptoUtil {
    static final String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    static final String decrypt(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
