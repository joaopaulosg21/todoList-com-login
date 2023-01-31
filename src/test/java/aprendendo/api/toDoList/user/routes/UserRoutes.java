package aprendendo.api.toDoList.user.routes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.model.DTO.UserDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserRoutes {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createUserRouteTest() {

        User user = new User();
        user.setEmail("emailparatests@email.com");
        user.setName("teste name");
        user.setPassword("123");

        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/users", user, UserDTO.class);
        
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(user.getEmail(),response.getBody().getEmail());
    }
}
