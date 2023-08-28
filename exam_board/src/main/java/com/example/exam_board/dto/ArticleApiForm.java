package com.example.exam_board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleApiForm {

    private Long id;
    private String title;
    private String content;
}
