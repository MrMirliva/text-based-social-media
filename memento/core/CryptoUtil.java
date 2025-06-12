package memento.core;

import java.util.Base64;
/**
 * Utility class providing simple encryption and decryption methods using Base64 encoding.
 * <p>
 * This class offers static methods to encode a string into Base64 format (as a form of simple "encryption")
 * and to decode a Base64-encoded string back to its original form.
 * </p>
 * <p>
 * Note: Base64 encoding is not a secure encryption method and should not be used for sensitive data.
 * </p>
 * 
 * @author Mirliva (Abdullah Gündüz)
 * @version 1.0
 * @since 2025-06-04
 */
final class CryptoUtil {
    /**
     * Encrypts the given input string by encoding it using Base64 encoding.
     *
     * @param input the plain text string to be encrypted
     * @return the Base64-encoded representation of the input string
     */
    static final String encrypt(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    /**
     * Decrypts the given Base64-encoded string back to its original plain text form.
     *
     * @param input the Base64-encoded string to be decrypted
     * @return the original plain text string
     */
    static final String decrypt(String input) {
        return new String(Base64.getDecoder().decode(input));
    }
}
