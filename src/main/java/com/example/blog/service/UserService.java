package com.example.blog.service;

import com.example.blog.DTO.CommentUserDTO;
import com.example.blog.DTO.UserRegisterDTO;
import com.example.blog.entities.Role;
import com.example.blog.entities.User;
import com.example.blog.repositories.RoleRepository;
import com.example.blog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log
@Service
@AllArgsConstructor
public class  UserService implements UserDetailsService {

    private static final String DEFAULT_ROLE = "ROLE_USER";
    private final BCryptPasswordEncoder bcryptEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> blogUser = userRepository.findByUsername(username);
        if (blogUser.isPresent()) {
            return blogUser.get();
        } else {
            throw new UsernameNotFoundException("No user found with username " + username);
        }
    }


        public User saveNewBlogUser(User user) throws RoleNotFoundException {

        user.setPassword(this.bcryptEncoder.encode(user.getPassword()));
        user.setActive(true);
        Optional<Role> optionalRole = this.roleRepository.findByRole(DEFAULT_ROLE);

        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            List<Role> roles = Collections.singletonList(role);
            user.setUser_roles(roles);

            return this.userRepository.saveAndFlush(user);
        }
        else {
            Role role = new Role("ROLE_USER");
            roleRepository.save(role);
            List<Role> roles = Collections.singletonList(role);
            user.setUser_roles(roles);
            return this.userRepository.saveAndFlush(user);
        }
    }

    public void loginNewUser(User user) {
        Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getUser_roles());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public User saveAdmin(String username, String password, String email) throws RoleNotFoundException {

        User user = new User();
        user.setPassword(this.bcryptEncoder.encode(password));
        user.setUsername(username);
        user.setEmail(email);
        // set account to enabled by default
        user.setActive(true);

        Role role = new Role("ROLE_ADMIN");
        roleRepository.save(role);
        List<Role> roles = Collections.singletonList(role);
        user.setUser_roles(roles);

        return this.userRepository.saveAndFlush(user);
    }
    
    public boolean findAdmin(Principal principal) {
        String name = principal.getName();
        log.severe(name);
        User user = userRepository.findByUsername(name).get();
        List<Role> roles = user.getUser_roles();
        for (Role role : roles)
        {
            if(role.getRole().equals("ROLE_ADMIN")) { return true; }
        }
        return false;
    }

    public boolean isAuthor(List<User> users, String username) {
        for (User user:users)
        {
            if(user.getUsername().equals(username)) { return true; }
        }
        return false;
    }

    public boolean isCommentAuthor(String user, String username) {
        return user.equals(username);
    }

    public boolean canEditComment(List<User> users, String user, String username)
    {
        return isAuthor(users, username) || isCommentAuthor(user, username);
    }

    public String getLoggedUserUsername(Principal principal) {
        String username="";
        if(principal != null) {
            username = principal.getName();
        }
        return username;
    }

    public User commentUserDTOtoUser(CommentUserDTO commentUserDTO) {
        User user = new User(commentUserDTO.getUsername());
        return user;
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

    public User UserRegisterDTOtoUser(UserRegisterDTO userRegisterDTO)
    {
        User user = new User(userRegisterDTO.getId(), userRegisterDTO.getEmail());
        return user;
    }
}
