package com.abcode.joblist.controller;

import com.abcode.joblist.model.Post;
import com.abcode.joblist.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);
     final private PostRepository repo;

    public PostController(PostRepository repo) {
        this.repo = repo;
    }


    @ApiIgnore
    @RequestMapping(value="/")
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    }

    @GetMapping("/allPosts")
    @CrossOrigin
    public List<Post> getAllPosts()
    {
        return repo.findAll();
    }


    @PostMapping("/post")
    @CrossOrigin
    public Post addPost(@RequestBody Post post)
    {
        return repo.save(post);
    }

    @DeleteMapping("/post/{id}")
    @CrossOrigin
    public ResponseEntity<String> deletePost(@PathVariable String id)
    {
        repo.deleteById(id);
        return new ResponseEntity<>("Post has been deleted!", HttpStatus.OK);
    }

    @PutMapping(value = "/post/{id}")
    @CrossOrigin
    public ResponseEntity<Post> updatePost(@PathVariable("id") String id, @RequestBody Post post)
    {
        // use the id to find coming from the path variable and update the post
        Post postToUpdate = repo.findById(id).get();
        logger.info("Post to update: " + postToUpdate);
        postToUpdate.setProfile(post.getProfile());
        postToUpdate.setDesc(post.getDesc());
        postToUpdate.setExp(post.getExp());
        postToUpdate.setTechs(post.getTechs());
        repo.save(postToUpdate);
        return new ResponseEntity<>(postToUpdate, HttpStatus.OK);
    }


    // Get all posts by profile using query method
    @GetMapping("/posts")
    @CrossOrigin
    public List<Post> getPostsByProfile(@RequestParam(required = false) String profile) {
        if (profile != null) {
            return repo.findByProfile(profile);
        } else {
            return repo.findAll();
        }
    }




}