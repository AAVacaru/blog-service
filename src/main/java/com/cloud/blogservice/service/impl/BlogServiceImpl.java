package com.cloud.blogservice.service.impl;

import com.cloud.blogservice.exception.NotFoundException;
import com.cloud.blogservice.model.Blog;
import com.cloud.blogservice.repository.BlogRepository;
import com.cloud.blogservice.service.BlogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("blogService")
@AllArgsConstructor
@Slf4j
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    public List<Blog> findAll() {
        List<Blog> blogs = new LinkedList<>();
        blogRepository.findAll().iterator().forEachRemaining(blogs::add);
        return blogs;
    }

    @Override
    public Blog findById(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if(blogOptional.isEmpty()) {
            log.info("Error when trying to find the blog with id {}", blogId);
            throw new NotFoundException("Blog with id " + blogId + "not found!");
        }
        log.info("Returned blog with id {}", blogId);
        return blogOptional.get();
    }

    @Override
    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findPaginated(Pageable pageable) {
        List<Blog> blogs = findAll();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Blog> list;
        if (blogs.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, blogs.size());
            list = blogs.subList(startItem, toIndex);
        }
        Page<Blog> blogPage = new PageImpl<>(list, PageRequest.of(currentPage,
                pageSize), blogs.size());
        return blogPage;
    }


    @Override
    public Page<Blog> findPaginatedAndSorted(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long blogId) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);
        if(blogOptional.isEmpty()) {
            throw new NotFoundException("Blog with id: " + blogId + "not found");
        }
        blogRepository.deleteById(blogId);
        log.info("Successfully deleted blog with id {}", blogId);
    }
}
