package org.example.blog.Controller;

import lombok.RequiredArgsConstructor;
import org.example.blog.Domain.Article;
import org.example.blog.Service.BlogService;
import org.example.blog.dto.AddArticleRequest;
import org.example.blog.dto.ArticleResponse;
import org.example.blog.dto.UpdateArticleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

    private final BlogService blogService;

    @GetMapping("/api/article/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @PutMapping("/api/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    @DeleteMapping("/api/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request){
        Article saveArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
    }


}
