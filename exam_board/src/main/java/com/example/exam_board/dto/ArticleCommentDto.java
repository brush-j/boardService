package com.example.exam_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String nickname;


}
