package aprendendo.api.toDoList.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService{
    
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isPresent()) {
            return (UserDetails) optionalUser.get();
        }
        throw new RuntimeException("User not found");
    }
}
