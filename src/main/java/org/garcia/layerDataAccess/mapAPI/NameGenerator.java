package org.garcia.layerDataAccess.mapAPI;

import java.util.Random;

public class NameGenerator {
    
    private static final int NAME_LENGTH = 9;
    
    public static String getRandomName() {
        String[] consonants = {"q","w","r","t","y","u","p","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m"};
        String[] vowels = {"a","e","i","o","u"};
        StringBuilder name = new StringBuilder("tp-");
        int currentLength = 0;

        do {
            name.append(getRandom(consonants));
            name.append(getRandom(vowels));
            currentLength += 2;

        } while (currentLength < NAME_LENGTH);

        return name.toString();
    }

    private static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }


}
