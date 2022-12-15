package com.cloud.blogservice.service;

import com.cloud.blogservice.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {

    List<Blog> findAll();
    Blog findById(Long blogId);
    Blog save(Blog blog);
    Page<Blog> findPaginated(Pageable pageable);
    Page<Blog> findPaginatedAndSorted(Pageable pageable);
    void deleteById(Long blogId);
}
