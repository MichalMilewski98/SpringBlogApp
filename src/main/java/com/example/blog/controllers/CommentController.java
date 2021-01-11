package com.example.blog.controllers;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.entities.Comment;
import com.example.blog.service.CommentService;
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
public class CommentController {

    private CommentService commentService;

    @PostMapping(value = "/comment")
    public String createNewComment(@ModelAttribute @Valid CommentDTO commentDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors())
            return "redirect:/";
        Comment comment = commentService.commentDtoToComment(commentDTO);
        commentService.save(comment);

        return "redirect:/post/" + comment.getPost().getId();
    }

    @GetMapping(value = "/comment/{id}")
    public String showComment(@PathVariable Long id, Model model) {

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setPost_id(id);
        model.addAttribute("comment", commentDTO);
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
    public String getEditComment(@PathVariable Long id, Model model) {

       Comment currentComment = commentService.getComment(id);
       CommentDTO commentDTO = commentService.commentToCommentDto(currentComment);
       model.addAttribute("comment", commentDTO);
        return "edit_comment";
    }

    @PostMapping(value = "/update_comment/{id}")
    public String editComment(@Valid @ModelAttribute CommentDTO commentDTO, BindingResult result) {

        Comment comment = commentService.commentDtoToComment(commentDTO);
        commentService.save(comment);
        return "redirect:/post/" + commentDTO.getPost_id();
    }
}
