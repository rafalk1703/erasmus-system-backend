package pl.agh.edu.erasmus_system.controller;

import lombok.Getter;

/**
 * POJO class with parameter needed to check session
 */
@Getter
public class SessionRequestBody {
    private String sessionCode;

    public SessionRequestBody() {}
}