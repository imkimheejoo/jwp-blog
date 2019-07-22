package techcourse.myblog.service;

import org.springframework.stereotype.Service;
import techcourse.myblog.article.Article;
import techcourse.myblog.article.ArticleRepository;
import techcourse.myblog.exception.NotFoundObjectException;
import techcourse.myblog.service.dto.ArticleDto;

import javax.transaction.Transactional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity();
        articleRepository.save(article);
        return article;
    }

    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(NotFoundObjectException::new);
    }

    @Transactional
    public Article updateArticle(Long articleId, Article updatedArticle) {
        Article article = findArticle(articleId);
        article.update(updatedArticle);

        return article;
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}