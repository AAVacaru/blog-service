package com.cloud.blogservice.converter;

import com.cloud.blogservice.dto.BlogDto;
import com.cloud.blogservice.model.Blog;
import com.cloud.blogservice.model.CategoryEnum;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlogConverter extends BaseConverter<BlogDto, Blog> {
    @Override
    public BlogDto fromEntityToDto(Blog entity) {
        return BlogDto.builder()
                .blogId(entity.getBlogId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory().name())
                .date(entity.getDate())
                .authorName(entity.getAuthorName())
                .pictures(entity.getPictures())
                .build();
    }

    @Override
    public Blog fromDtoToEntity(BlogDto dto) {
        return Blog.builder()
                .blogId(dto.getBlogId())
                .title(dto.getTitle())
                .category(CategoryEnum.valueOf(dto.getCategory()))
                .content(dto.getContent())
                .date(LocalDateTime.now())
                .authorName(dto.getAuthorName())
                .build();
    }
}
