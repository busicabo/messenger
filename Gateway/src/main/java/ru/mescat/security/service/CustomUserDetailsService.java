package ru.mescat.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mescat.security.User;
import ru.mescat.security.exception.RemoteServiceException;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = userService.info(username);
        } catch (RemoteServiceException e) {
            throw new UsernameNotFoundException("Неверные данные!", e);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getId().toString(),
                user.getPassword(),
                true,
                true,
                true,
                !user.isBlocked(),
                List.of()
        );
    }
}
