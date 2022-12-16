package com.cloud.blogservice.service;

import com.cloud.blogservice.model.Blog;

import java.util.List;

public interface BlogService {

    List<Blog> findAll();
    Blog findById(Long blogId);
    Blog save(Blog blog);
    void deleteById(Long blogId);
}
