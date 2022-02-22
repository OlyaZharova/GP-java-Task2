package com.gp_solutions.task2.controller;

import com.gp_solutions.task2.model.Developer;
import com.gp_solutions.task2.model.JwtRequest;
import com.gp_solutions.task2.security.JwtUtil;
import com.gp_solutions.task2.service.DeveloperNotFoundException;
import com.gp_solutions.task2.service.DeveloperService;
import com.gp_solutions.task2.service.IncorrectDataException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "DeveloperController", tags = {"Developer Controller"})
@SwaggerDefinition(tags = {
        @Tag(name = "Developer Controller", description = "Rest API for developer operations")
})
@RestController
@RequestMapping("/api")
public class DeveloperController {

    @Autowired
    private DeveloperService developerService;
    @Autowired
    private JwtUtil jwtUtil;


    @ApiOperation(value = "Find developer by id", response = Developer.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "404 error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/user/developers/{id}")
    public ResponseEntity<?> findDeveloper(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(developerService.findDeveloper(id), HttpStatus.OK);
        } catch (DeveloperNotFoundException exception) {
            return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Create developer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 404, message = "404 error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer",
                    required = true, dataType = "string", paramType = "header")})
    @PostMapping("/admin/developers")
    public ResponseEntity<?> createDeveloper(@RequestBody Developer developer) {
        try {
            developerService.createDeveloper(developer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IncorrectDataException exception) {
            return new ResponseEntity<>(exception.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update developer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "404 error"),
            @ApiResponse(code = 400, message = "400 error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping("/admin/developers")
    public ResponseEntity<?> updateDeveloper(@RequestBody Developer developer) {
        try {
            developerService.updateDeveloper(developer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IncorrectDataException exception) {
            return new ResponseEntity<>(exception.toString(), HttpStatus.BAD_REQUEST);
        } catch (DeveloperNotFoundException exception) {
            return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete developer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "404 error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer",
                    required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/admin/developers/{id}")
    public ResponseEntity<?> deleteDeveloper(@PathVariable long id) {
        try {
            developerService.deleteDeveloper(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeveloperNotFoundException exception) {
            return new ResponseEntity<>(exception.toString(), HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "List of all developers", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "404 error")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token", defaultValue = "Bearer",
                    required = true, dataType = "string", paramType = "header")})
    @GetMapping("/user/developers")
    public ResponseEntity<List<Developer>> getAllDeveloper() {
        List<Developer> developerList = developerService.getAllDeveloper();
        if (!developerList.isEmpty()) {
            return ResponseEntity.ok().body(developerList);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Generate token", response = String.class)
    @PostMapping("/builder-jwt")
    public ResponseEntity<String> builderJWT(@RequestBody JwtRequest jwtRequest) {
        String token = jwtUtil.generateAccessToken(jwtRequest);
        return ResponseEntity.ok().body(token);
    }

}
