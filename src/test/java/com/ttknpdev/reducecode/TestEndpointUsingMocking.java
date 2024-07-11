package com.ttknpdev.reducecode;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TestEndpointUsingMocking {

    private HttpServletRequest request;
    private  HttpServletResponse response;
    private UserServlet userServlet;
    private final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0dGtucGRldiIsImlhdCI6MTcyMDQyMjQwMCwiZXhwIjoxODA2ODIyNDAwfQ.BO4CKMwVB1nieM-IAp0gCQjFsx_VaFbQkud_kzmcsTM";
    private final String DEMO_RESPONSE = "{\"token\":\"" + TOKEN + "\"}";
    private final String DEMO_RESPONSE_AUTH_TOKEN = "Bearer " + TOKEN;

    @BeforeEach
    public void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        userServlet = new UserServlet();
    }

    // ** Using Mocking Frameworks
    /*
    Some of their strong points are their expressibility and the out-of-the-box ability to mock static and private methods.
    Further, we can avoid most of the boilerplate code needed for mocking (compared to custom implementations) and instead focus on the tests.
    */
    @Test
    public void testLogin() throws IOException {

        String location = "B:\\txts\\response.txt";
        PrintWriter printWriter = new PrintWriter(location);

        // mock the returned value of request.getParameter()
        when(request.getParameter("username")).thenReturn("peter");
        when(request.getParameter("password")).thenReturn("12345");
        // now printWriter has a location to save data meaning  response.getWriter() in doLogin(...) can write files to this location
        when(response.getWriter()).thenReturn(printWriter);

        // then pass request and response
        userServlet.doLogin(request, response);

        // after doLogin(...) done request.getParameter(<key>) has value , response.getWriter() has value
        String response = getResponse(location);
        assertEquals(DEMO_RESPONSE,response);

    }

    private String getResponse(String location) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(location));
        String stringOnCurrentLine;
        String response = "";
        while ((stringOnCurrentLine = bufferedReader.readLine()) != null) {
            response = response + stringOnCurrentLine;
        }
        return response;
    }


    @Test
    public void testAuthToken() throws IOException {
        String location = "B:\\txts\\token.txt";
        PrintWriter printWriter = new PrintWriter(location);

        // now printWriter has a location to save data meaning  response.getWriter() in doLogin(...) can write files to this location
        when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
        when(response.getWriter()).thenReturn(printWriter); // set object had location

        userServlet.doAuthToken(request, response);

        // read file if doAuthToken(...) done smooth
        String response = getResponse(location);

        assertEquals(DEMO_RESPONSE_AUTH_TOKEN,response);
    }

    // inner class
    public class UserServlet extends HttpServlet {

        public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // get parameters
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            PrintWriter printWriter = response.getWriter();

            if (username.equals("peter") && password.equals("12345")) {
                // load PrintWriter object
                // write file
                printWriter.append(DEMO_RESPONSE); // note , it writes new it's not overwrite

            } else {
                printWriter.append("{\"token\": \"}"); // note , it writes new it's not overwrite
            }
            // may close
            printWriter.flush();
            printWriter.close();
        }

        public void doAuthToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // get parameters
            String authentication = request.getHeader("Authorization");
            PrintWriter printWriter = response.getWriter();
            if (authentication.startsWith("Bearer ") && authentication.equals(DEMO_RESPONSE_AUTH_TOKEN)) { // just correct format
                printWriter.append(authentication); // then write file
            } else {
                printWriter.append("");
            }
            // may close
            printWriter.flush();
            printWriter.close();
        }

    }
}
