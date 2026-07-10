package com.example.module5.service;

import com.example.module5.DTO.PostDTO;
import com.example.module5.entities.PostEntity;
import com.example.module5.exceptions.ResourceNotFoundException;
import com.example.module5.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImplementation implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        var postEntity = modelMapper.map(postDTO, PostEntity.class);
        var saveEntity = postRepository.save(postEntity);
        return modelMapper.map(saveEntity,PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<PostEntity> postEntityList = postRepository.findAll();
        return postEntityList
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .toList();
    }

    @Override
    public PostDTO getPostById(Long id) {
        var postEntity = postRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("post is not found " + id));
        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(PostDTO inputPost, Long id) {
        PostEntity oldPost= postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post is not found " + id));
        inputPost.setId(id);
        modelMapper.map(inputPost,oldPost);
        PostEntity savedPostEntity = postRepository.save(oldPost);
        return modelMapper.map(savedPostEntity,PostDTO.class);
    }
}
