package com.example.blog.controllers;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.DTO.CommentUserDTO;
import com.example.blog.DTO.PostDTO;
import com.example.blog.DTO.UserRegisterDTO;
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

    @PostMapping(value = "/comment")
    public String createNewComment(@ModelAttribute @Valid CommentUserDTO commentUserDTO, Principal principal, Model model) throws RoleNotFoundException {
        Optional<User> optionalUser = userService.getUser(userService.getLoggedUserUsername(principal));

        if(optionalUser.isPresent()) {
            commentUserDTO.setUser(optionalUser.get().getUsername());
            Comment comment = commentService.commentUserDTOtoComment(commentUserDTO);
            commentService.save(comment);

            return "redirect:/post/" + comment.getPost().getId();
        }
        else {
            model.addAttribute("user", commentService.createCommentWithoutAccount(commentUserDTO));

            return "comment_register";
        }
    }

    @PostMapping(value = "/commentRegister")
    public String commentRegister(@ModelAttribute UserRegisterDTO userRegisterDTO) throws RoleNotFoundException {
        //user.setPassword(PasswordGeneration.generatePassword());
        User user = userService.getUserById(userRegisterDTO.getId()).get();
        user.setEmail(userRegisterDTO.getEmail());
        emailService.sendEmail(user, user.getPassword());
        userService.save(user);

        return "index";
    }

    @GetMapping(value = "/comment/{id}")
    public String showComment(@PathVariable Long id, Model model) {

        CommentUserDTO commentUserDTO = new CommentUserDTO();
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
    public String deleteComment(@PathVariable Long id, Principal principal) {

        Comment comment = commentService.getComment(id);
        Optional<User> optionalUser = userService.getUser(userService.getLoggedUserUsername(principal));

        if (optionalUser.isPresent()) {
            Long postId = comment.getPost().getId();
            Post post = postService.getPost(postId);
            if (userService.canEditComment(post.getPost_authors(), principal.getName(), comment.getUser().getUsername()))
            {
                commentService.delete(comment);
            }
            else
                return "redirect:/post/" + comment.getPost().getId();
        }
        return "redirect:/post/" + comment.getPost().getId();
    }

    @GetMapping(value = "/edit_comment/{id}")
    public String getEditComment(@PathVariable Long id, Model model, Principal principal) {

        Optional<User> optionalUser = userService.getUser(userService.getLoggedUserUsername(principal));

        if (optionalUser.isPresent())
        {
            Comment currentComment = commentService.getComment(id);
            CommentDTO commentDTO = commentService.commentToCommentDto(currentComment);
            model.addAttribute("comment", commentDTO);

            return "edit_comment";
        }
        else {
            return "index";
        }
    }

    @PostMapping(value = "/update_comment/{id}")
    public String editComment(@Valid @ModelAttribute CommentDTO commentDTO, Principal principal) {

        Optional<User> optionalUser = userService.getUser(userService.getLoggedUserUsername(principal));
        Long postId = commentDTO.getPost_id();
        Post post = postService.getPost(postId);

        if (optionalUser.isPresent()) {

            if(userService.canEditComment(post.getPost_authors(), optionalUser.get().getUsername(),commentDTO.getUser())) {
                Comment comment = commentService.commentDtoToComment(commentDTO);
                commentService.save(comment);

                return "redirect:/post/" + commentDTO.getPost_id();
            }
                return "index";
        }
            return "index";
    }

}
