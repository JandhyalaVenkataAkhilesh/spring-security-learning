package com.example.module5.service;

import com.example.module5.DTO.PostDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostService {
    public PostDTO createNewPost(PostDTO postDto);
    public List<PostDTO> getAllPosts();
    public PostDTO getPostById(Long id);

    PostDTO updatePostById(PostDTO inputDTO, Long id);
}
