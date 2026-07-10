package com.example.module5.controller;

import com.example.module5.DTO.PostDTO;
import com.example.module5.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public PostDTO create(@RequestBody PostDTO inputDTO){
        return postService.createNewPost(inputDTO);
    }

    @GetMapping
    public List<PostDTO> getAll(){
        return postService.getAllPosts();
    }

    @GetMapping(path = "/{id}")
    public PostDTO getId(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @PutMapping(path = "/{id}")
    public PostDTO update(@RequestBody PostDTO inputDTO,@PathVariable Long id){
        return postService.updatePostById(inputDTO,id);
    }
}
