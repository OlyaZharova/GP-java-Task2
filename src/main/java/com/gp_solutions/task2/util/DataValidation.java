package com.gp_solutions.task2.util;

import com.gp_solutions.task2.model.Developer;

import java.util.List;

public class DataValidation {

    public boolean validationName(String name) {
        return name.length() >= 2 && name.length() <= 50 && name.substring(0, 1).matches("^[a-zA-Z]*$");
    }

    public boolean validationEmail(List<Developer> developerList, Developer developer) {
        boolean flag = true;
        for (Developer developerFromList :
                developerList) {
            if (developerFromList.getEmail().equals(developer.getEmail()) && developerFromList.getId() != developer.getId()) {
                flag = false;
            }
        }
        return flag;

    }
}
