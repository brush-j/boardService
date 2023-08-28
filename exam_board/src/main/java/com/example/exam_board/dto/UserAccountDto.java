package com.example.exam_board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    @NotBlank(message = "id를 입력하세요")
    private String userId;
    @NotBlank(message = "비밀번호를 입력하세요")
    private String userPassword;
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;
}
