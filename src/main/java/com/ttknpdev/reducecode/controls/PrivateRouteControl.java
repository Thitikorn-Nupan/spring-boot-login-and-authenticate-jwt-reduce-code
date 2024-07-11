package com.ttknpdev.reducecode.controls;

import com.ttknpdev.reducecode.entities.Book;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/store")
public class PrivateRouteControl {

    // restricted endpoint (add. หวงห้าม , จำกัด)
    @GetMapping(value = "/books")
    private ResponseEntity retrieveAllBooks ()  {
        return ResponseEntity.ok(List.of(
                new Book(1L,"Java Core 2022","Stone Austin",350.0F),
                new Book(2L,"C&C++ Core 2020","Mark Ryder",450.0F),
                new Book(3L,"Angular & Node.js for Beginner 2023","Alex Slider",650.0F),
                new Book(4L,"Php Core 2019","Stone Austin",310.0F)
        ));
    }

    // unrestricted
    @GetMapping(value = "/")
    private ResponseEntity testEndpoint() {
       //  throw new TokenExpiredNotAllowed("Test endpoint not allowed");
       return ResponseEntity.ok("Hello World");
    }
}

