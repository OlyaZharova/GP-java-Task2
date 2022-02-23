package com.gp_solutions.task2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class JwtRequest {
    private String issuer;
    private String subject;
    private Date expirationDate;
    private List<Role> roles;
}
