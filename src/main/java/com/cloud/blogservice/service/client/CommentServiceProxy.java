package com.cloud.blogservice.service.client;

import com.cloud.blogservice.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "comment-service")
public interface CommentServiceProxy {

    @GetMapping(value = "/comment-service/blogs/{blogId}/comments", consumes = "application/json")
    ResponseEntity<List<CommentDto>> getAllCommentsByBlogId(@PathVariable Long blogId);

}
