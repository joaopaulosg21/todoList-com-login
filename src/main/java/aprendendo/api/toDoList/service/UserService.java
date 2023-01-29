package aprendendo.api.toDoList.service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import aprendendo.api.toDoList.auth.TokenService;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.model.DTO.LoginDTO;
import aprendendo.api.toDoList.model.DTO.TokenDTO;
import aprendendo.api.toDoList.model.DTO.UserDTO;
import aprendendo.api.toDoList.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;
    
    private final TokenService tokenService;

    public UserService(UserRepository userRepository,TokenService tokenService,AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    public UserDTO addUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepository.save(user).toDTO();
        }

        throw new RuntimeException("Email já está em uso");
    }

    public TokenDTO login(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generate(authentication);
        return TokenDTO.builder().token(token).type("Bearer").build();

    }
}
