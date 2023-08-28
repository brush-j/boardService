package com.example.exam_board.controller;

import com.example.exam_board.dto.ArticleCommentDto;
import com.example.exam_board.dto.ArticleForm;
import com.example.exam_board.dto.UserAccountDto;
import com.example.exam_board.entity.Article;
import com.example.exam_board.entity.ArticleComment;
import com.example.exam_board.entity.UserAccount;
import com.example.exam_board.repository.ArticleCommentRepository;
import com.example.exam_board.repository.ArticleRepository;
import com.example.exam_board.repository.UserAccountRepository;
import com.example.exam_board.service.PageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
public class BoardController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/list")
    public String main(Model model){
        List<Article> articleList = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        model.addAttribute("list", articleList);

        return "view";
    }

    @GetMapping("/{id}")
    public String contentDetail(Model model, @PathVariable("id") Long id){
        Optional<Article> article = articleRepository.findById(id);
        ArticleForm articleForm = new ArticleForm();

        articleForm.setId(article.get().getId());
        articleForm.setContent(article.get().getContent());
        articleForm.setEmail(article.get().getUserAccount().getEmail());
        articleForm.setTitle(article.get().getTitle());
        articleForm.setNickname(article.get().getUserAccount().getNickname());
        articleForm.setUserId(article.get().getUserAccount().getUserId());
        articleForm.setCreatedAt(article.get().getCreatedAt());
        articleForm.setCreatedBy(article.get().getCreatedBy());
        articleForm.setModifiedAt(article.get().getModifiedAt());
        articleForm.setModifiedBy(article.get().getModifiedBy());

        List<ArticleComment> articleCommentList = article.get().getArticleCommentList();

        List<ArticleCommentDto> articleComments = new ArrayList<>();

        for (ArticleComment a : articleCommentList){
            ArticleCommentDto dto = new ArticleCommentDto();
            dto.setContent(a.getContent());
            dto.setId(a.getId());
            dto.setNickname(a.getUserAccount().getNickname());
            dto.setCreatedAt(a.getCreatedAt());

            articleComments.add(dto);
        }


        model.addAttribute("dto", articleForm);
        model.addAttribute("listDto", articleComments);
        model.addAttribute("comment", new ArticleComment());

        return "/articles/detail";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String insertArticleForm(Model model){
        model.addAttribute("dto", new ArticleForm());

        return "/articles/new";

    }

    @Autowired
    UserAccountRepository userAccountRepository;
    @PostMapping("/create")
    public String insertArticle(UserAccount userAccount, Principal principal,
                                @Valid @ModelAttribute("dto") ArticleForm articleForm,
                                BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "/articles/new";
        }

        Article article1 = new Article();

        Optional<UserAccount> userAccount1 = userAccountRepository.findById(principal.getName());

        article1.setId(articleForm.getId());
        article1.setContent(articleForm.getContent());
        article1.setTitle(articleForm.getTitle());
        article1.setUserAccount(userAccount1.get());

        articleRepository.save(article1);

        return "redirect:/articles/lists";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteArticle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        articleRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "삭제되었습니다.");
        return "redirect:/articles/lists";
    }

    @GetMapping("/{id}/update")
    @PreAuthorize("isAuthenticated()")
    public String updateArticleForm(@PathVariable("id") Long id,
                                    Model model){

        Optional<Article> article = articleRepository.findById(id);

        ArticleForm article1 = new ArticleForm();
        article1.setId(article.get().getId());
        article1.setTitle(article.get().getTitle());
        article1.setContent(article.get().getContent());
        article1.setUserId(article.get().getUserAccount().getUserId());

        model.addAttribute("dto", article1);

        return "/articles/update";

    }

    @PostMapping("/update")
    public String updateArticleCommit(@Valid @ModelAttribute("dto") ArticleForm article,
                                      BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "/articles/update";
        }

        Optional<UserAccount> userAccount1 = userAccountRepository.findById("min");

        Article article1 = new Article();
        article1.setId(article.getId());
        article1.setTitle(article.getTitle());
        article1.setContent(article.getContent());
        article1.setUserAccount(userAccount1.get());
        article1.setArticleCommentList(articleCommentRepository.findAll());

        articleRepository.save(article1);

        return "redirect:/articles/lists";
    }

    @Autowired
    ArticleCommentRepository articleCommentRepository;

    @PostMapping("/{id}/articleComment")
    @PreAuthorize("isAuthenticated()")
    public String createArticleComment(@PathVariable("id") Long id,
                                       @RequestParam String content, Principal principal){
        Optional<UserAccount> userAccount1 = userAccountRepository.findById(principal.getName()); // UserAccount userAccount1 = em.find(UserAccount.class, "min");
        Optional<Article> article1 = articleRepository.findById(id); // Article article1 = em.find(Article.class, id);

        ArticleComment articleComment = new ArticleComment();
        articleComment.setId(articleComment.getId());
        articleComment.setContent(content);
        articleComment.setArticle(article1.get());
        articleComment.setUserAccount(userAccount1.get());

        articleCommentRepository.save(articleComment);

        return "redirect:/articles/{id}";
    }

    @PostMapping("/{id}/articleComments/{article-comment-id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String articleCommentsDelete(@PathVariable("article-comment-id") Long id){

        articleCommentRepository.deleteById(id.toString());

        return "redirect:/articles/{id}";
    }
    @Autowired
    PageService pageService;
    @GetMapping("/lists")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "type", required = false) String type){
        Page<Article> list = null;

        if(keyword == null || type == null){
            list = pageService.boardList(pageable);
        }else {
            switch (type){
                case "title":
                    list = pageService.search(keyword, pageable);
                    break;
                case "content":
                    list = pageService.searchContent(keyword, pageable);
                    break;
                case "userId":
                    list = pageService.searchUserId(keyword, pageable);
                    break;
                case "nickname":
                    list = pageService.searchNickname(keyword, pageable);
                    break;
                case "id":
                    list = pageService.searchId(keyword, pageable);
                    break;
            }
        }

        int startPage = Math.max(1, list.getPageable().getPageNumber() - 1);
        int endPage = Math.min(list.getPageable().getPageNumber() + 3, list.getTotalPages());
        int lastPage = Math.max(1, list.getTotalPages() - 1);

        model.addAttribute("list", list);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("lastPage", lastPage);

        return "view";
    }

    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("dto", new UserAccountDto());
        return "joinForm";
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("dto") UserAccountDto dto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "joinForm";

        }
            UserAccount account = new UserAccount();

            account.setUserId(dto.getUserId());
            account.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
            account.setEmail(dto.getEmail());
            account.setNickname(dto.getNickname());
            account.setRole("ROLE_USER");

            userAccountRepository.save(account);

        return "redirect:/articles/loginForm";
    }
}
