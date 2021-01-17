package com.example.blog.controllers;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.DTO.CommentUserDTO;
import com.example.blog.DTO.PostDTO;
import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.entities.Validation.PasswordGeneration;
import com.example.blog.service.CommentService;
import com.example.blog.service.EmailService;
import com.example.blog.service.PostService;
import com.example.blog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log
@Controller
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;
    private UserService userService;
    private PostService postService;
    private EmailService emailService;

  /*  @PostMapping(value = "/comment")
    public String createNewComment(@ModelAttribute @Valid CommentDTO commentDTO, @ModelAttribute @Valid User user,BindingResult bindingResult, Principal principal, Model model) throws RoleNotFoundException {
        log.severe("COS TAM");
        String username="";
        if(principal != null) {
            username = principal.getName();
        }
        Optional<User> optionaluser = userService.getUser(username);

        if(bindingResult.hasErrors())
        {
            log.severe("BINDING ERROR");
            return "redirect:/";
        }


        if(optionaluser.isPresent()) {
            commentDTO.setUser(optionaluser.get().getUsername());
            Comment comment = commentService.commentDtoToComment(commentDTO);
            commentService.save(comment);
            log.severe("JEST ZALOGOWANY");
            return "redirect:/post/" + comment.getPost().getId();
        }
        else
        {
            log.severe("NIE JEST ZALOGOWANY");
            //userService.saveNewBlogUser(user);
            //commentDTO.setUser(user.getUsername());
            //Comment comment = commentService.commentDtoToComment(commentDTO);
            //commentService.save(comment);
            //model.addAttribute("user", user);
            //return "comment_register";
            return "redirect:/";
        }
    }
    */

    @PostMapping(value = "/comment")
    public String createNewComment(@ModelAttribute @Valid CommentUserDTO commentUserDTO, BindingResult bindingResult, Principal principal) throws RoleNotFoundException {
        log.severe("COS TAM");
        String username="";
        if(principal != null) {
            username = principal.getName();
        }
        Optional<User> optionaluser = userService.getUser(username);

        if(bindingResult.hasErrors())
        {
            log.severe("BINDING ERROR");
            return "redirect:/";
        }


        if(optionaluser.isPresent()) {
            commentUserDTO.setUser(optionaluser.get().getUsername());
            Comment comment = commentService.commentUserDTOtoComment(commentUserDTO);
            commentService.save(comment);
            log.severe("JEST ZALOGOWANY");
            return "redirect:/post/" + comment.getPost().getId();
        }
        else
        {
            log.severe("NIE JEST ZALOGOWANY");
            User user = userService.commentUserDTOtoUser(commentUserDTO);
            user.setEmail("michal.milewski98@gmail.com");
            //user.setPassword("PASSWORD");
            user.setPassword(PasswordGeneration.generatePassword());
            emailService.sendEmail(user, user.getPassword());
            userService.saveNewBlogUser(user);
            commentUserDTO.setUser(user.getUsername());
            Comment comment = commentService.commentUserDTOtoComment(commentUserDTO);
            commentService.save(comment);
            //model.addAttribute("user", user);
            //return "comment_register";
            return "redirect:/";
        }
    }

    @GetMapping(value = "/comment/{id}")
    public String showComment(@PathVariable Long id, Model model) {

       /* CommentDTO commentDTO = new CommentDTO();
        User user = new User();
        commentDTO.setPost_id(id);
        model.addAttribute("comment", commentDTO);
        model.addAttribute("user", user);
        return "/new_comment";

        */

        CommentUserDTO commentUserDTO = new CommentUserDTO();
        //User user = new User();
        commentUserDTO.setPost_id(id);
        model.addAttribute("comment", commentUserDTO);
        return "/new_comment";
    }

    @GetMapping(value = "/post/{username}/comments")
    public String getUserComments(@PathVariable String username, Model model) {

        List<Comment> comments = commentService.findCommentsByUser(username);
        model.addAttribute("comments", comments);

        return "/user_comments";
    }

    @GetMapping(value = "/delete_comment/{id}")
    public String deleteComment(@PathVariable Long id) {

        Comment comment = commentService.getComment(id);
        commentService.delete(comment);
        return "redirect:/post/" + comment.getPost().getId();
    }

    @GetMapping(value = "/edit_comment/{id}")
    public String getEditComment(@PathVariable Long id, Model model, Principal principal) {

        String username="";
        if(principal != null) {
            username = principal.getName();
        }

        Optional<User> optionaluser = userService.getUser(username);



        if (optionaluser.isPresent())
        {
            Comment currentComment = commentService.getComment(id);
            CommentDTO commentDTO = commentService.commentToCommentDto(currentComment);
            model.addAttribute("comment", commentDTO);
            return "edit_comment";
        }
        else
        {
            return "index";
        }

    }

    @PostMapping(value = "/update_comment/{id}")
    public String editComment(@Valid @ModelAttribute CommentDTO commentDTO, Principal principal) {

        String username="";
        if(principal != null) {
            username = principal.getName();
        }
        Optional<User> optionaluser = userService.getUser(username);
        log.severe("comment user : " + commentDTO.getUser());
        log.severe(principal.getName());
        Long postId = commentDTO.getPost_id();
        Post post = postService.getPost(postId);
        List<User> authors = post.getPost_authors();

        if (optionaluser.isPresent())
        {
            if(username.equals(commentDTO.getUser()) || userService.isAuthor(authors, username))
            {
                Comment comment = commentService.commentDtoToComment(commentDTO);
                commentService.save(comment);
                return "redirect:/post/" + commentDTO.getPost_id();
            }
            else
            {
                log.severe("principal get name");
                return "index";
            }
        }
        else
        {
            log.severe("optional not found");
            return "index";
        }

    }
}
