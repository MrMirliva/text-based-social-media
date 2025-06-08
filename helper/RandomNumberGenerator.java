package helper;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code RandomNumberGenerator} class provides utility methods for generating random integers within a specified range,
 * with the ability to exclude certain numbers. This class is designed to be reusable across different projects.
 * <p>
 * Written by Mirliva (Abdullah Gündüz).
 * Since 25.04.2025.
 *
 * <p>The primary method, {@link #generateRandomInt(int, int, int, int[])}, generates a list of unique random integers between
 * the given {@code min} and {@code max} values, excluding any integers specified in the {@code dontUse} array. The method
 * ensures that the returned list contains exactly {@code numberOf} elements, provided enough valid numbers are available.
 *
 * <p><b>Time Complexity:</b>
 * <ul>
 *   <li>{@code getRandomNumbers}: O(n) for list initialization and shuffling (n = max - min + 1).</li>
 *   <li>Exclusion of {@code dontUse} elements: O(k·n), where k = length of {@code dontUse} (each removal from LinkedList is O(n)).</li>
 *   <li>{@code generateRandomInt} overall: O(n + k·n).</li>
 * </ul>
 *
 * <p><b>Note:</b> {@code generateRandomInt} returns a view backed by the shuffled list. If you need an independent copy,
 * wrap the result in a new {@code ArrayList<>(…)}.
 *
 * @author Mirliva (Abdullah Gündüz)
 * @version 2.0
 * @since 25.04.2025
 */

public class RandomNumberGenerator {
    
    //TO_VERIFY
    /**
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @param numberOf the number of random integers to generate
     * @param dontUse an array of integers to exclude from the generated list
     * @return a list of unique random integers within the specified range,
     *         excluding the integers specified in the {@code dontUse} array.
     */
    public static List<Integer> generateRandomInt(int min, int max, int numberOf, int[] dontUse) {
        List<Integer> randomNumbers = getRandomNumbers(min, max);
    
        for (int i = 0; i < dontUse.length; i++) {
            randomNumbers.remove(Integer.valueOf(dontUse[i]));
        }

        return randomNumbers.subList(0, numberOf);
    }

    
    private static List<Integer> getRandomNumbers(int min, int max) {
        List<Integer> randomNumbers = new LinkedList<>();

        for(int i = min; i <= max; i++) {
            randomNumbers.add(i);
        }

        for(int i = 0; i < randomNumbers.size(); i++) {
            int randomIndex = newIndex(i, 0, randomNumbers.size() - 1);
            int temp = randomNumbers.get(i);
            randomNumbers.set(i, randomNumbers.get(randomIndex));
            randomNumbers.set(randomIndex, temp);
        }

        return randomNumbers;
    }

    private static int newIndex(int num, int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }
}
