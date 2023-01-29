package aprendendo.api.toDoList.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import aprendendo.api.toDoList.auth.TokenService;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.model.DTO.UserDTO;
import aprendendo.api.toDoList.repository.UserRepository;
import aprendendo.api.toDoList.service.UserService;
@ExtendWith(MockitoExtension.class)
public class UserTests {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    private UserService userService;

    private User user;

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository,tokenService,authenticationManager);

        this.user = new User(1L,"testes","teste@email.com","123",null);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    public void userCreateTest() {

        this.user = new User(1L,"testes","teste@email.com","123",null);
        UserDTO response = userService.addUser(user);

        Assertions.assertEquals(this.user.getEmail(),response.getEmail());
    }
}
