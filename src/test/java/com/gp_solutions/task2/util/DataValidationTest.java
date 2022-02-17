package com.gp_solutions.task2.util;

import com.gp_solutions.task2.model.Developer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataValidationTest {

    private static List<Developer> developerList;
    private DataValidation dataValidation = new DataValidation();

    @BeforeAll
    static void setup() {
        developerList = new ArrayList<>();
        Developer developer1 = new Developer(1L, "Olga", "olga@gmail.com");
        developerList.add(developer1);
        Developer developer2 = new Developer(2L, "Daria", "daria@gmail.com");
        developerList.add(developer2);
    }

    @Test
    void validationName() {
        boolean result = dataValidation.validationName("Olga");
        assertTrue(result);
    }

    @Test
    void validationNameIncorrect() {
        boolean resultWithNumber = dataValidation.validationName("1Olga");
        assertFalse(resultWithNumber);
        boolean resultWithPunctuation = dataValidation.validationName("1Olga");
        assertFalse(resultWithPunctuation);
        boolean resultWithOneSymbol = dataValidation.validationName("o");
        assertFalse(resultWithOneSymbol);
        boolean resultWithMoreSymbol = dataValidation.validationName("fhfhffhhfhfhfhfhfhhfjdsjjjuejedjksjdksjdjdjksjdkkjs");
        assertFalse(resultWithMoreSymbol);

    }

    @Test
    void validationEmail() {
        Developer developer = new Developer(3L, "Elena", "elena@gmail.com");
        boolean result = dataValidation.validationEmail(developerList, developer);
        assertTrue(result);
    }

    @Test
    void validationEmailIncorrect() {
        Developer developerWithSameEmail = new Developer(3L, "Elena", "daria@gmail.com");
        boolean resultWithSameEmail = dataValidation.validationEmail(developerList, developerWithSameEmail);
        assertFalse(resultWithSameEmail);
        Developer developerWithSameId = new Developer(2L, "Elena", "daria@gmail.com");
        boolean resultWithSameId = dataValidation.validationEmail(developerList, developerWithSameId);
        assertTrue(resultWithSameId);
    }
}