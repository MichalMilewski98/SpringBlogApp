package com.example.blog.service;

import com.example.blog.DTO.CommentDTO;
import com.example.blog.entities.Comment;
import com.example.blog.entities.User;
import com.example.blog.entities.exception.PostNotFoundException;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostService postService;
    private UserService userService;

    public Comment save(Comment comment) {
        return commentRepository.saveAndFlush(comment);
    }

    public Comment getComment(Long id) {return commentRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));}

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> findAllComments(){return commentRepository.findAll();}

    public List<Comment> findCommentsByUser(String username)
    {
        return commentRepository.findCommentsByUser(userRepository.findUserByUsername(username));
    }

    public User getCommentAuthor(String author)
    {
        if(userRepository.findByUsername(author) == null)
        {
            User user = new User(author);
            userService.save(user);
            return user;
        }
        else
        {
            return (userRepository.findUserByUsername(author));
        }
    }

    public Comment commentDtoToComment(CommentDTO commentDTO)
    {
        Comment comment = new Comment(commentDTO.getId(), commentDTO.getBody(), getCommentAuthor(commentDTO.getUser()), postService.getPost(commentDTO.getPost_id()));
        return comment;
    }

    public CommentDTO commentToCommentDto(Comment comment)
    {
        CommentDTO commentDTO = new CommentDTO(comment.getId(), comment.getBody(), comment.getUser().getUsername(), comment.getPost().getId());
        return commentDTO;
    }

}
