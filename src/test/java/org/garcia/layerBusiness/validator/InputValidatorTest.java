package org.garcia.layerBusiness.validator;


import org.garcia.layerView.util.InputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class InputValidatorTest {
    @Mock
    String correctLocation = "Wien";
    String correctLocation_lc = "wien";
    String germanLocation = "sankt pölten";
    String hyphenLocation = "sankt-pölten";
    String wrongLocation = "Gr@z";
    String viennaAddress = "Landstraße 45-50, 1030, Wien, Österreich";

    @Test
    @DisplayName("Validate wrong and correct input")
    public void locationStringTest() {
        Assertions.assertTrue(InputValidator.validString(correctLocation));
        Assertions.assertTrue(InputValidator.validString(correctLocation_lc));
        Assertions.assertTrue(InputValidator.validString(germanLocation));
        Assertions.assertTrue(InputValidator.validString(hyphenLocation));
        Assertions.assertTrue(InputValidator.validString(viennaAddress));
        Assertions.assertFalse(InputValidator.validString(wrongLocation));
    }
}
