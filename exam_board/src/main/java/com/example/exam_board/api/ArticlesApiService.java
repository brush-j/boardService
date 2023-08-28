package com.example.exam_board.api;

import com.example.exam_board.dto.ArticleApiForm;
import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.entity.Article;
import com.example.exam_board.entity.ArticleComment;
import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.repository.ArticleCommentRepository;
import com.example.exam_board.repository.ArticleRepository;
import com.example.exam_board.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ArticlesApiService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleCommentRepository commentRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @GetMapping("/api/articles")
    public List<ArticleApiForm> view() {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleApiForm> apiForm = new ArrayList<>();

        for (Article a : articleList) {
            ArticleApiForm articleApiForm = new ArticleApiForm();

            articleApiForm.setId(a.getId());
            articleApiForm.setTitle(a.getTitle());
            articleApiForm.setContent(a.getContent());

            apiForm.add(articleApiForm);
        }
        return apiForm;
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleApiForm> getOneView(@PathVariable Long id) {

        Article article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        ArticleApiForm apiForm = new ArticleApiForm();
        apiForm.setId(article.getId());
        apiForm.setTitle(article.getTitle());
        apiForm.setContent(article.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(apiForm);

    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> patch(@PathVariable Long id,
                                                @RequestBody Article form) {

        Article article = articleRepository.findById(id).orElse(null);
        if (article == null || id != article.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        article.setTitle(form.getTitle());
        article.setContent(form.getContent());

        articleRepository.save(article);

        return ResponseEntity.status(HttpStatus.OK).body(article);

    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {

        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            articleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        }
    }

    @PostMapping("/api/articles/article")
    public Article insert(@RequestBody Article form) {
        Article article1 = new Article();

        article1.setContent(form.getContent());
        article1.setTitle(form.getTitle());

        return articleRepository.save(article1);

    }

    @GetMapping("/api/articles/{id}/comments")
    public ResponseEntity<?> commentList(@PathVariable Long id){

        Article article = articleRepository.findById(id).orElse(null);

        if(article == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {

            List<ArticleComment> commentList = articleRepository.findById(id).get().getArticleCommentList();
            List<ArticleCommentDto> list = new ArrayList<>();

            for (ArticleComment a : commentList) {
                ArticleCommentDto commentDto = new ArticleCommentDto();

                commentDto.setId(a.getId());
                commentDto.setContent(a.getContent());
                commentDto.setCreatedAt(a.getCreatedAt());
                commentDto.setNickname(a.getUserAccount().getNickname());

                list.add(commentDto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
    }

    @GetMapping("/api/articles/{id}/comments/{a_id}")
    public ResponseEntity<?> commentOne(@PathVariable Long id,
                                        @PathVariable Long a_id) {

        Article article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            ArticleComment comment = commentRepository.findById(String.valueOf(a_id)).orElse(null);

            if (comment == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else {
                ArticleCommentDto dto = new ArticleCommentDto();

                dto.setId(comment.getId());
                dto.setContent(comment.getContent());
                dto.setNickname(comment.getUserAccount().getNickname());
                dto.setCreatedAt(comment.getCreatedAt());

                return ResponseEntity.status(HttpStatus.OK).body(dto);
            }
        }
    }

    @PostMapping("/api/articles/{id}/comment")
    public ArticleComment commentInsert(@PathVariable Long id,
                                        @RequestBody ArticleComment comment){

        Optional<UserAccount> userAccount1 = userAccountRepository.findById("min");
        Optional<Article> article1 = articleRepository.findById(id);

        ArticleComment articleComment = new ArticleComment();
        articleComment.setContent(comment.getContent());
        articleComment.setArticle(article1.get());
        articleComment.setUserAccount(userAccount1.get());

        return commentRepository.save(articleComment);
    }

    @DeleteMapping("/api/articles/{id}/comments/{a_id}")
    public void deleteComment(@PathVariable Long a_id){

       commentRepository.deleteById(a_id.toString());

    }

    @PatchMapping("/api/articles/{id}/comments/{a_id}")
    public ResponseEntity<?> patchComment(@PathVariable Long id,
                                          @PathVariable Long a_id,
                                          @RequestBody ArticleComment comment){

        Article article = articleRepository.findById(id).orElse(null);

        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            ArticleComment comment1 = commentRepository.findById(String.valueOf(a_id)).orElse(null);

            if (comment == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            } else {

                Optional<UserAccount> userAccount1 = userAccountRepository.findById("min");
                Optional<Article> article1 = articleRepository.findById(id);

                comment1.setContent(comment.getContent());
                comment1.setArticle(article1.get());
                comment1.setUserAccount(userAccount1.get());

                commentRepository.save(comment1);
                return ResponseEntity.status(HttpStatus.OK).body(comment1);
            }
        }
    }
}