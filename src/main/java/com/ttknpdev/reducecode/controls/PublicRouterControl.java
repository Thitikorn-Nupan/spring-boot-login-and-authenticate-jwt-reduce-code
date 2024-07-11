package com.ttknpdev.reducecode.controls;

import com.ttknpdev.reducecode.entities.User;
import com.ttknpdev.reducecode.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/public")
public class PublicRouterControl {

    private UserService userService;
    private Logger logger;

    @Autowired
    public PublicRouterControl(UserService userService) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(PublicRouterControl.class);
    }

    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Map response = userService.userLoggedInAndGetToken(user);
        // response : {token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dGtucGRldiIsImlhdCI6MTcyMDQyMjQwMCwiZXhwIjoxODA2ODIyNDAwfQ.BO4CKMwVB1nieM-IAp0gCQjFsx_VaFbQkud_kzmcsTM}
        // may null if logged in failed
        return ResponseEntity.ok(response);
    }


    /**
    @PostMapping(value = "/authenticate-token")
    public ResponseEntity authToken(@RequestParam String token) {
        return ResponseEntity.ok(userService.authenticationToken(token));
    }
    */

}
