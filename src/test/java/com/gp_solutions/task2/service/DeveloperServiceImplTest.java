package com.gp_solutions.task2.service;

import com.gp_solutions.task2.model.Developer;
import com.gp_solutions.task2.repository.DeveloperRepository;
import com.gp_solutions.task2.util.DataValidation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeveloperServiceImplTest {

    @Autowired
    private DeveloperService developerService;

    @MockBean
    private DeveloperRepository developerRepository;

    @MockBean
    private DataValidation dataValidation;

    private static Developer developer;
    private static List<Developer> developerList;

    @BeforeAll
    static void setUp() {
        developer = new Developer(1L, "Olga", "olga@gmail.com");
        developerList = new ArrayList<>();
        Developer developer1 = new Developer(1L, "Olga", "olga@gmail.com");
        developerList.add(developer1);
        Developer developer2 = new Developer(2L, "Daria", "daria@gmail.com");
        developerList.add(developer2);
    }

    @Test
    void findDeveloper() throws DeveloperNotFoundException {
        Mockito.when(developerRepository.findById(developer.getId())).thenReturn(Optional.of(developer));
        assertEquals(developerService.findDeveloper(developer.getId()), developer);
    }

    @Test
    void notFindDeveloper() {
        try {
            Mockito.when(developerRepository.findById(developer.getId())).thenReturn(Optional.empty());
            assertEquals(developerService.findDeveloper(developer.getId()), developer);
        } catch (DeveloperNotFoundException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }


    @Test
    void createDeveloperWithSameEmail() {
        Developer developerWithSameEmail= new Developer(3L, "Daria", "daria@gmail.com");
        try {
            Mockito.when(developerService.getAllDeveloper()).thenReturn(developerList);
            developerService.createDeveloper(developerWithSameEmail);
        } catch (IncorrectDataException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

    @Test
    void createDeveloperWithIncorrectName() {
        Developer developerWithIncorrectName = new Developer(1L, "1Olga", "olga@gmail.com");
        try {
            Mockito.when(dataValidation.validationName(developerWithIncorrectName.getName())).thenReturn(false);
            developerService.createDeveloper(developerWithIncorrectName);
        } catch (IncorrectDataException exception) {
            assertNotEquals("", exception.getMessage());
        }
    }

   /* @Test
    void updateDeveloper() {
    }

    @Test
    void getAllDeveloper() {
    }

    @Test
    void deleteDeveloper() {
    }*/
}