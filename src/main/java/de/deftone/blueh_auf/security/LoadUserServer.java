package de.deftone.blueh_auf.security;

import de.deftone.blueh_auf.registration.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class LoadUserServer implements UserDetailsService {

    private final UserRepo userRepo;

    // damit User aus DB und nicht application.prop geholt werden
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        de.deftone.blueh_auf.registration.User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user");
        }
        return new User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>());
    }
}