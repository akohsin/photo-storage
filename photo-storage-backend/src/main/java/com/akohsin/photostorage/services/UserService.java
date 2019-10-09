package com.akohsin.photostorage.services;

import com.akohsin.photostorage.dto.AuthorizationDto;
import com.akohsin.photostorage.entities.User;
import com.akohsin.photostorage.repositories.UserRepository;
import com.akohsin.photostorage.security.SecurityConstants;
import com.akohsin.photostorage.security.TokenGenerator;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private final String emailPattern = "[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenGenerator tokenGenerator;

    @Autowired
    public UserService(UserRepository userRepository, TokenGenerator tokenGenerator, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.getByEmail(email);
        if (!byEmail.isPresent()) {
            throw new UsernameNotFoundException(email);
        } else {
            User user = byEmail.get();
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
        }
    }

    public Optional<User> getByToken(HttpServletRequest req) {
        return userRepository.getByEmail(tokenGenerator.parseEmail(req));
    }

    public boolean createUser(AuthorizationDto authorizationDto) {
        String email = authorizationDto.getEmail();
        if (email.matches(emailPattern) && !userRepository.getByEmail(email).isPresent()) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(authorizationDto.getPassword()));
            userRepository.save(user);
            return true;
        } else return false;
    }

    public void updateEmail(String newEmail, User user) {
        user.setEmail(newEmail);
        userRepository.save(user);
    }

    public Optional<User> getByEmail(String newEmail) {
        return userRepository.getByEmail(newEmail);
    }
}
