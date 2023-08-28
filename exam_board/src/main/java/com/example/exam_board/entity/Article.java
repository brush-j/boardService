package com.example.exam_board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/* @Table


 */
@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articleId")
    private Long id;

    @JoinColumn(name = "userId")
    @ManyToOne
    private UserAccount userAccount; //유저정보(ID)

    @Column(nullable = false)
    private String title; //제목

    @Column(nullable = false, length = 10000)
    private String content;

    //게시글 삭제할때 달려있는 댓글도 같이 삭제하기 위해(영속성 전이 : 같이 commit됨)
    //cascade를 사용하고 게시글 삭제후 남은 댓글. 즉, 고아객체삭제를 위해 orphanRemoval 사용
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleComment> articleCommentList = new ArrayList<>();


}
