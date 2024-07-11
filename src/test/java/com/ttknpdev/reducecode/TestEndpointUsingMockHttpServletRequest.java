package com.ttknpdev.reducecode;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestEndpointUsingMockHttpServletRequest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private UserServlet userServlet;
    private Logger logger;
    private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dGtucGRldiIsImlhdCI6MTcyMDQyMjQwMCwiZXhwIjoxODA2ODIyNDAwfQ.BO4CKMwVB1nieM-IAp0gCQjFsx_VaFbQkud_kzmcsTM";
    private final String DEMO_RESPONSE = "{\"token\":\"" + TOKEN + "\"}";

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        userServlet = new UserServlet();
        logger = LoggerFactory.getLogger(TestEndpointUsingMockHttpServletRequest.class);
    }

    @Test
    public void testLogin() throws IOException {
        //set param
        request.setParameter("username", "peter");
        request.setParameter("password", "12345");

        // then pass request and response
        userServlet.doLogin(request, response);


        logger.info(response.getContentAsString());
        // now i will get some response key That i set on doLogin(...)
        assertEquals("peter 12345",response.getContentAsString());
    }

    @Test
    public void testLogin2() throws IOException {
        // set param
        request.setParameter("username", "peter");
        request.setParameter("password", "12345");

        // then pass request and response
        userServlet.doLogin2(request, response);
        // now i will get response value That i set on doLogin2(...)
        assertEquals( DEMO_RESPONSE , response.getContentAsString());
    }
    /*
        Here, we can notice that there is no actual mocking involved.
        We have used the fully functional request and response objects and tested the target class with just a few lines of code.
        As a result, the test code is clean, readable, and maintainable.
    */

    public class UserServlet extends HttpServlet {

        public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // get parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // set response
            response.getWriter().append(username + " " + password);
        }

        public void doLogin2(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // get parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            // set response
            if (username.equals("peter") && password.equals("12345")) {
                response.getWriter().append(DEMO_RESPONSE);
            } else {
                response.getWriter().append("");
            }
        }

    }


}
