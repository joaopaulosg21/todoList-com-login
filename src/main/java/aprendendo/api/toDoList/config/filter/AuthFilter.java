package aprendendo.api.toDoList.config.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import aprendendo.api.toDoList.auth.TokenService;
import aprendendo.api.toDoList.model.User;
import aprendendo.api.toDoList.repository.UserRepository;

public class AuthFilter extends OncePerRequestFilter{

    private UserRepository userRepository;

    private TokenService tokenService;

    public AuthFilter(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,FilterChain chain) 
    throws ServletException,IOException{

        String token = getTokenFromHeader(req);
        if(tokenService.isValid(token)){
            authenticate(token);
        }

        doFilter(req, res, chain);
    }

    public String getTokenFromHeader(HttpServletRequest req) {
        String token = req.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.split(" ")[1];
    }

    public void authenticate(String token) {
        long id = tokenService.getIdFromToken(token);
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePassword);
        }
    }
}
