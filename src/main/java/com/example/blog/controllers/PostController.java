package com.example.blog.controllers;

import com.example.blog.DTO.PostDTO;
import com.example.blog.entities.Post;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@AllArgsConstructor
public class PostController {

    private PostService postService;
    private UserService userService;

    @GetMapping(value = "/post/{id}/json")
    public PostDTO getPostInJson(@PathVariable Long id) {

        Post post = postService.getPost(id);
        PostDTO postDTO = postService.postToPostDTO(post);

        return postDTO;
    }

    @GetMapping(value = "/{username}/json")
    public List<PostDTO> getAuthorPostsInJson(@PathVariable String username) {

        return postService.getUserPosts(userService.getUser(username).get())
                .stream()
                .map(postService::postToPostDTO)
                .collect(Collectors.toList());

    }

}
