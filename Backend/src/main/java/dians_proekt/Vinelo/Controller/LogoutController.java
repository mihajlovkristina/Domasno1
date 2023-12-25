package dians_project.vinelo.controller;

import dians_project.vinelo.model.User;
import dians_project.vinelo.service.AuthService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final AuthService authService;

    public LogoutController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public HttpStatus logout(HttpServletRequest request) throws ExecutionException, InterruptedException {
        request.getSession().invalidate();
        return HttpStatus.ACCEPTED;
    }
}