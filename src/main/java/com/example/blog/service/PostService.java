package com.example.blog.service;

import com.example.blog.DTO.PostDTO;
import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.entities.exception.PostNotFoundException;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private UserService userService;

    public List<Post> getAllPosts()
    {
        return postRepository.findAll();
    }

    public void insert(Post post)
    {
        postRepository.save(post);
    }

    public void save(List<Post> posts) {postRepository.saveAll(posts);}

    public Post getPost(Long id) {return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));}

    public void delete(Long id) {postRepository.deleteById(id);}

    public List<Post> findByKeyword(String keyword){return postRepository.findByKeyword(keyword);}

    public List<User> authorList(String author)
    {
        List<String> listOfStrings = Arrays.asList(author.split("[ ,]+"));
        List<User> users = new ArrayList<>();
        for (var autor : listOfStrings)
        {
            Optional<User> user = userRepository.findByUsername(autor);
            users.add(user.get());
        }
        return users;
    }

    public String usernamesList(List<User> users)
    {
        String usernames ="";
        for (var user : users)
        {
            usernames += user.getUsername() + ",";
        }

        return usernames;
    }

    public List<Post> sortPosts(String sortField)
    {
        Sort sort = Sort.by(sortField).ascending();
        return postRepository.findAll(sort);
    }

    public Post postDTOtoPost(PostDTO postDTO)
    {
        Post post = new Post(postDTO.getId(), authorList(postDTO.getPost_authors()), postDTO.getTitle(), postDTO.getPost_content(), postDTO.getTag());
        return post;
    }

    public PostDTO postToPostDTO(Post post)
    {
        PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(), post.getPost_content(), usernamesList(post.getPost_authors()), post.getTag());
                return postDTO;
    }
}
