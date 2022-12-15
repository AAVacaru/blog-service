package com.cloud.blogservice.model;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "blog")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;

    @NotEmpty(message = "Title cannot be empty!")
    private String title;

    @NotEmpty(message = "Blog content cannot be empty!")
    private String content;

    @Column(columnDefinition = "enum('TRAVEL', 'FOOD', 'FASHION','OTHER')")
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    private LocalDateTime date;

    @NotEmpty(message = "Author name cannot be empty!")
    @Column(name = "author_name")
    private String authorName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "blog_pictures",
            joinColumns = {
                @JoinColumn(name = "blog_id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "picture_id")
            }
    )
    private Set<Picture> pictures;

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
