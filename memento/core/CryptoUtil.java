package memento.core;

import java.util.Base64;

///TO_VERIFY ///DOC: CryptoUtil sınıfını kontrol et ve gerekli açıklamaları ekle
final class CryptoUtil {
    static final String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    static final String decrypt(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
