package com.gp_solutions.task2.service;

import com.gp_solutions.task2.model.Developer;
import com.gp_solutions.task2.repository.DeveloperRepository;
import com.gp_solutions.task2.util.DataValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    private final DataValidation dataValidation = new DataValidation();

    @Override
    public Developer findDeveloper(Long id) throws DeveloperNotFoundException {
        Optional<Developer> developer = developerRepository.findById(id);
        if (developer.isPresent()) {
            return developer.get();
        } else {
            throw new DeveloperNotFoundException("Record not found with id : " + id);
        }
    }

    @Override
    public void createDeveloper(Developer developer) throws IncorrectDataException {
        if (dataValidation.validationName(developer.getName())) {
            if (dataValidation.validationEmail(getAllDeveloper(), developer)) {
                developerRepository.save(developer);
            } else {
                throw new IncorrectDataException("Email is not unique");
            }
        } else {
            throw new IncorrectDataException("Name is not correct");
        }
    }

    @Override
    public void updateDeveloper(Developer developer) throws DeveloperNotFoundException, IncorrectDataException {
        Developer developerUpdate = findDeveloper(developer.getId());
        if (dataValidation.validationName(developer.getName())) {
            if (dataValidation.validationEmail(getAllDeveloper(), developer)) {
                developerUpdate.setName(developer.getName());
                developerUpdate.setEmail(developer.getEmail());
                developerRepository.save(developerUpdate);
            } else {
                throw new IncorrectDataException("Email is not unique");
            }
        } else {
            throw new IncorrectDataException("Name is not correct");
        }
    }

    @Override
    public List<Developer> getAllDeveloper() {
        return developerRepository.findAll();
    }

    @Override
    public void deleteDeveloper(Long id) throws DeveloperNotFoundException {
        Developer developer = findDeveloper(id);
        developerRepository.delete(developer);
    }
}
