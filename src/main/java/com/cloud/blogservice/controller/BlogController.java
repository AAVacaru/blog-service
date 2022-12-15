package com.cloud.blogservice.controller;


import com.cloud.blogservice.converter.BlogConverter;
import com.cloud.blogservice.dto.BlogDto;
import com.cloud.blogservice.dto.CommentDto;
import com.cloud.blogservice.dto.ResponseDto;
import com.cloud.blogservice.model.Blog;
import com.cloud.blogservice.model.Picture;
import com.cloud.blogservice.service.BlogService;
import com.cloud.blogservice.service.client.CommentServiceProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/blog-service/blogs")
public class BlogController {

    private final BlogService blogService;
    private final BlogConverter blogConverter;
    private final CommentServiceProxy commentServiceProxy;

    @GetMapping()
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        List<Blog> blogs = blogService.findAll();
        return ResponseEntity.ok(blogConverter.fromEntitiesToDtos(blogs));
    }

    @GetMapping("/{blogId}")
    @CircuitBreaker(name = "blogWithComments", fallbackMethod = "getBlogWithCommentsFallback")
    public ResponseEntity<ResponseDto> getById(@PathVariable("blogId") Long blogId) {
        Blog blog = blogService.findById(blogId);
        List<CommentDto> comments = commentServiceProxy.getAllCommentsByBlogId(blogId).getBody();
        return ResponseEntity.ok(new ResponseDto(blogConverter.fromEntityToDto(blog), comments));
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Blog> createNewBlog(@RequestPart("blog") BlogDto blog,
                                              @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<Picture> pictures = uploadImage(file);
            Blog newBlog = blogConverter.fromDtoToEntity(blog);
            newBlog.setPictures(pictures);
            newBlog = blogService.save(newBlog);
            return ResponseEntity.created(URI.create("/blog-service/blogs/" + newBlog.getBlogId())).body(newBlog);
        } catch (Exception e) {
            log.error("Error in adding a new blog: {}", e.getMessage(), e);
            return null;
        }
    }

    public ResponseEntity<Blog> getBlogWithCommentsFallback(Long blogId, Throwable throwable) {
        Blog blog = blogService.findById(blogId);
        log.warn("For blog with id: {} the comments could not be loaded.", blogId);

        return ResponseEntity.ok().body(blog);
    }

    @DeleteMapping("/{blogId}")
    public void deleteBlog(@PathVariable("blogId") Long blogId) {
        blogService.deleteById(blogId);
    }

    public Set<Picture> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<Picture> pictures = new HashSet<>();

        for (MultipartFile file : multipartFiles) {
            Picture picture = new Picture(file.getOriginalFilename(), file.getContentType(), file.getBytes());
            pictures.add(picture);
        }

        return pictures;
    }

}
