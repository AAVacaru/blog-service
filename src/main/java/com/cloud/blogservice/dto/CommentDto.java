package com.cloud.blogservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;
    private String content;
    private String authorName;
    private LocalDateTime date;
    private Long blogId;
}
