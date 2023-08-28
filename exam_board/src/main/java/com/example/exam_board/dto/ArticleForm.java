package com.example.exam_board.dto;

import com.example.exam_board.entity.AuditingFields;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {


    private Long id;
    @NotBlank(message = "제목을 입력하셔야 합니다.")
    private String title;
    @NotBlank(message = "내용을 입력하셔야 합니다.")
    private String content;
    private String email;
    private String nickname;
    private String userId;
    private String contentC;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String createdBy;
    private String modifiedBy;

}
