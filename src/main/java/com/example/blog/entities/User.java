package com.example.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Wrong email format")
    @NotEmpty(message = "Field Email cant be empty")
    private String email;



    @NotEmpty(message = "Filed Username cant be empty")
    @Length(max = 30, message = "Username cant be longer than 30 characters")
    private String username;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "Field password cant be empty")
    @JsonIgnore
    private String password;

    @Column(name = "active", nullable = false)
    private int active;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post_authors")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<User> user_roles = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public void addPost(Post post)
    {
        posts.add(post);
    }

    User(){}

    @Override
    public String toString() {
        return "" + username;
    }

}
