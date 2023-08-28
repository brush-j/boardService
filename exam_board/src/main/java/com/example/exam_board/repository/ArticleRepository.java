package com.example.exam_board.repository;

import com.example.exam_board.entity.Article;
import com.example.exam_board.entity.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTitleContaining(String keyword, Pageable pageable);
    //select * from article where title like "%검색어%"
    Page<Article> findByContentContaining(String keyword, Pageable pageable);
    //select * from article where content like "%검색어%"
    Page<Article> findById(Long keyword, Pageable pageable);

    Page<Article> findByUserAccount_UserIdContains(String keyword, Pageable pageable);
    Page<Article> findByCreatedByContaining(String keyword, Pageable pageable);

}
