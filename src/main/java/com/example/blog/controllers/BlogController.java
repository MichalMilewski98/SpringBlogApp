package com.example.blog.controllers;

import com.example.blog.DTO.PostDTO;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Log
@Controller
@AllArgsConstructor
public class BlogController {


    private PostService postService;
    private UserService userService;

    @GetMapping(value = "/posts")
    public List<Post> posts()
    {
        return postService.getAllPosts();
    }

    @GetMapping(value = "/delete/{id}")
    public List <Post> remove(@PathVariable Long id)
    {
        postService.delete(id);
        return postService.getAllPosts();
    }

    @GetMapping("/")
    public String getHomepage(Model model, String keyword)
    {
        List<Post> posts = postService.getAllPosts();
        List<Post> filteredPosts = postService.findByKeyword(keyword);
        if(keyword!=null)
            model.addAttribute("posts", filteredPosts);
        else
            model.addAttribute("posts", posts);

        return "index";
    }

    @GetMapping("/sort")
    public String sortPosts(Model model, @RequestParam("sortField") String sortField)
    {
        List<Post> posts = postService.sortPosts(sortField);
        model.addAttribute("sortField", sortField);
        model.addAttribute("posts", posts);

        return "index";
    }

    @PostMapping(value = "/new_post")
    public String publishPost(@Valid @ModelAttribute PostDTO postDTO, BindingResult bindingResult, Principal principal)
    {
        if (bindingResult.hasErrors()) {
            return "new_post";
        }
        log.severe("principal :" + principal.getName());
        String username = principal.getName();
        //if (principal != null) {
        //    authUsername = principal.getName();
        //}
        Optional<User> optionaluser = userService.getUser(username);
        log.severe("optional :" + optionaluser.get().getUsername());

        postDTO.setPost_authors(optionaluser.get().getUsername());
        Post post = postService.postDTOtoPost(postDTO);

        postService.insert(post);

        return "redirect:/";
    }

    @GetMapping(value = "/new_post")
    public String publishPost(Model model, Principal principal)
    {
        log.severe(principal.getName());
        String username = principal.getName();
        //if (principal != null) {
        //    authUsername = principal.getName();
        //}
        Optional<User> optionaluser = userService.getUser(username);
        log.severe(optionaluser.get().getUsername());
        //if (optionaluser.isPresent()) {
            PostDTO postDTO = new PostDTO();

            model.addAttribute("post", postDTO);
       // }

        return "new_post";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model)
    {
        log.severe("GET POST!!!");
        Post currentPost = postService.getPost(id);

            model.addAttribute("post", currentPost);
            return "post";
    }

    @GetMapping("/delete_post/{id}")
    public String deletePost(@PathVariable Long id)
    {
        Post post = postService.getPost(id);
        postService.delete(id);

        return "redirect:/";
    }

    @GetMapping("edit_post/{id}")
    public String getEditPost(@PathVariable Long id, Model model)
    {
        Post currentPost = postService.getPost(id);
        PostDTO postDTO = postService.postToPostDTO(currentPost);
        model.addAttribute("post", postDTO);

        return "edit_post";
    }

    @PostMapping("update_post/{id}")
    public String editPost(@ModelAttribute PostDTO postDTO)
    {
        Post post = postService.postDTOtoPost(postDTO);
        postService.insert(post);

        return "redirect:/";
    }

}
