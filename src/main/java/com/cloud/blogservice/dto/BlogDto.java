package com.cloud.blogservice.dto;

import com.cloud.blogservice.model.Picture;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogDto {

    private Long blogId;
    private String title;
    private String content;
    private String category;
    private LocalDateTime date;
    private String authorName;
    private Set<Picture> pictures;
    private List<CommentDto> comments;
}
