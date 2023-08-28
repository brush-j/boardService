package com.example.exam_board.service;

import com.example.exam_board.entity.Article;
import com.example.exam_board.repository.ArticleRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    @Autowired
    ArticleRepository articleRepository;

    public Page<Article> boardList(Pageable pageable){
        return articleRepository.findAll(pageable);
    }


    @Transactional
    public Page<Article> search(String keyword, Pageable pageable){
        return articleRepository.findByTitleContaining(keyword, pageable);
    }

    @Transactional
    public Page<Article> searchContent(String keyword, Pageable pageable){
        return articleRepository.findByContentContaining(keyword, pageable);
    }

    @Transactional
    public Page<Article> searchId(String keyword, Pageable pageable){
        return articleRepository.findById(Long.parseLong(keyword), pageable);
    }

    @Transactional
    public Page<Article> searchUserId(String keyword, Pageable pageable){
        return articleRepository.findByUserAccount_UserIdContains(keyword, pageable);
    }

    @Transactional
    public Page<Article> searchNickname(String keyword, Pageable pageable){
        return articleRepository.findByCreatedByContaining(keyword, pageable);
    }

}
