package dians_project.vinelo.controller;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LoggedInUserCheck {

    private static LoggedInUserCheck instance;

    private LoggedInUserCheck() {}

    public static LoggedInUserCheck getInstance() {
        if (instance == null) {
            instance = new LoggedInUserCheck();
        }
        return  instance;
    }

    public boolean check(String token) {
        if (token == null || token.isEmpty() || !token.matches(".+###\\d{4}-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d")) {
            return false;
        }

        String[] tokenTime = token.split("###", 2);
        LocalDateTime tokenDateTime = LocalDateTime.parse(tokenTime[1], DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return !tokenDateTime.isBefore(LocalDateTime.now(ZoneId.of("GMT+1")));
    }
}