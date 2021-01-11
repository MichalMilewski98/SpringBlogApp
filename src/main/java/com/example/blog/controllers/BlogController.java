package com.example.blog.controllers;

import com.example.blog.DTO.PostDTO;
import com.example.blog.entities.Post;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Log
@Controller
@AllArgsConstructor
public class BlogController {


    private PostService postService;

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
    public String publishPost(@Valid @ModelAttribute PostDTO postDTO, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        Post post = postService.postDTOtoPost(postDTO);

        postService.insert(post);

        return "redirect:/";
    }

    @GetMapping(value = "/new_post")
    public String publishPost(Model model)
    {
        PostDTO postDTO = new PostDTO();
        model.addAttribute("post", postDTO);

        return "new_post";
    }

    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id, Model model)
    {
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
