package com.example.blog.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="roles")
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "user_roles")
    private Collection<User> users;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", users='" + users + '\'' +
//                ", users=" + users +
                '}';
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
