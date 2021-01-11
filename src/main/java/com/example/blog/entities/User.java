package com.example.blog.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post_authors")
    private List<Post> posts = new ArrayList<>();

    @NotEmpty(message = "Username cant be empty")
    @Length(max = 30, message = "Username cant be longer than 30 characters")
    private String username;

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
