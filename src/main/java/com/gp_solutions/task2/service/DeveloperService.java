package com.gp_solutions.task2.service;

import com.gp_solutions.task2.model.Developer;

import java.util.List;

public interface DeveloperService {


    Developer findDeveloper(Long id) throws DeveloperNotFoundException;

    void createDeveloper(Developer developer) throws IncorrectDataException;

    void updateDeveloper(Developer developer) throws DeveloperNotFoundException, IncorrectDataException;

    List<Developer> getAllDeveloper();

    void deleteDeveloper(Long id) throws DeveloperNotFoundException;

}
