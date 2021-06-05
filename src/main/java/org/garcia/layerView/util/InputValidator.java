package org.garcia.layerView.util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern LOCATION_PATTERN = Pattern.compile("[0-9a-zA-Z\\p{Space}äöüÄÖÜß,/'-]*");

    public static boolean validString(String input) {
        if(input.length() > 180 || input.length() == 0)
            return false;
        return LOCATION_PATTERN.matcher(input).matches();
    }
}

