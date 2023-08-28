package com.example.exam_board.service;

import com.example.exam_board.entity.Article;
import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.repository.ArticleRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class InputService {
    @Autowired
    EntityManager em;
    @Transactional
    void inputArticleTest(){
        UserAccount userAccount = em.find(UserAccount.class, "min");

        Article article = new Article();
        article.setTitle("연습");
        article.setContent("연습입니다.");
        article.setUserAccount(userAccount);
        userAccount.getArticles().add(article);

        em.persist(article);

    }




}
