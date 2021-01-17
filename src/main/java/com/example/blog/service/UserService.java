package com.example.blog.service;

import com.example.blog.entities.Role;
import com.example.blog.entities.User;
import com.example.blog.repositories.RoleRepository;
import com.example.blog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class  UserService implements UserDetailsService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final BCryptPasswordEncoder bcryptEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<User> blogUser = userRepository.findByUsername(username);
        if (blogUser.isPresent()) {
            return blogUser.get();
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
//        return blogUser.orElseThrow(() -> new UsernameNotFoundException("No user found with username \" + username"));
    }


        public User saveNewBlogUser(User user) throws RoleNotFoundException {

        user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
        // set account to enabled by default
        user.setActive(true);
        // Set default Authority/Role to new blog user
        Optional<Role> optionalRole = this.roleRepository.findByRole(DEFAULT_ROLE);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            List<Role> roles = Collections.singletonList(role);
            user.setUser_roles(roles);

            return this.userRepository.saveAndFlush(user);
        } else {
            throw new RoleNotFoundException("Default role not found for blog user with username " + user.getUsername());
        }
    }
    public boolean isAuthor(List<User> users, String username)
    {
        for (User user:users)
        {
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public Optional<User> getUserById(Long id) { return userRepository.findById(id); }

    public void save(User user){
        userRepository.save(user);
    }

    public Optional<User> getUser(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email); }
}
