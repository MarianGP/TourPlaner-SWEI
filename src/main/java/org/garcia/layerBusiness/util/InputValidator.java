package org.garcia.layerBusiness.util;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern LOCATION_PATTERN = Pattern.compile("[0-9a-zA-Z\\p{Space}äöüÄÖÜß,/'-]*");

    public static boolean validString(String input) {
        if(input.length() > 180)
            return false;
        return LOCATION_PATTERN.matcher(input).matches();
    }

    public static int validInt(Object input) {
        if(input == null)
            return 0;
        try {
            return (int) input;
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    public static boolean validateStringList(String[] inputs) {
        for(String input: inputs) {
            if(!validString(input))
                return false;
        }
        return true;
    }
}

