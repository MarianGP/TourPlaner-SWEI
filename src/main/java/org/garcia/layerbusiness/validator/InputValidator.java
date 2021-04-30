package org.garcia.layerbusiness.validator;

import java.util.regex.Pattern;

public class InputValidator {

    //range > 0 < 999999999

    private static final Pattern LOCATION_PATTERN =
            Pattern.compile("[0-9a-zA-Z\\p{Space}äöüÄÖÜß,/'-]*");

    public static boolean validString(String input) {
        if(input == null)
            return false;

        return LOCATION_PATTERN.matcher(input).matches();
    }

    public static float validFloat(String number) {
        if(number == null)
            return 0f;
        try {
            return Float.parseFloat(number);
        }
        catch (NumberFormatException ex) {
            return 0f;
        }
    }
}

