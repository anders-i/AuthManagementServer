/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

/**
 * TAKEN FROM
 * https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string/41156#41156
 */

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        //As long as there is buffer room
        for (int idx = 0; idx < buf.length; ++idx)
            //Add a random character from the symbols array to the array of characters to return.
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    //String of uppercase characters
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //String of lowercase characters from the uppercase string
    public static final String lower = upper.toLowerCase(Locale.ROOT);

    //All possible digits
    public static final String digits = "0123456789";

    //All possible characters
    public static final String alphanum = upper + lower + digits;

    private final Random random;

    //Characters to use
    private final char[] symbols;

    //Buffer
    private final char[] buf;

    /**
     * 
     * @param length of the random string, must be over 0
     * @param random object to use insted
     * @param symbols to generate from
     */
    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) {
        this(length, random, alphanum);
    }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Create session identifiers.
     */
    public RandomString() {
        this(21);
    }

}
