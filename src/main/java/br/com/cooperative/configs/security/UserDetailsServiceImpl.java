package br.com.cooperative.configs.security;

import br.com.cooperative.configs.CP;
import br.com.cooperative.exceptions.EntityNotFoundException;
import br.com.cooperative.models.entities.User;
import br.com.cooperative.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
        User userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User" + CP.NOT_FOUND + " email:" + email));
        return  new User(userModel.getUsername(), userModel.getPassword(), true, true, true,true, userModel.getAuthorities());
    }
}