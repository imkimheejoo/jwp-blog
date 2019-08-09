package techcourse.myblog.service;

import org.springframework.stereotype.Service;
import techcourse.myblog.domain.article.Article;
import techcourse.myblog.domain.article.ArticleRepository;
import techcourse.myblog.domain.article.Contents;
import techcourse.myblog.domain.comment.Comment;
import techcourse.myblog.domain.user.User;
import techcourse.myblog.service.assembler.ArticleAssembler;
import techcourse.myblog.service.assembler.CommentAssembler;
import techcourse.myblog.service.dto.ResponseCommentDto;
import techcourse.myblog.service.exception.NotFoundObjectException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(Contents contents, User author) {
        Article article = ArticleAssembler.toEntity(contents, author);
        return articleRepository.save(article);
    }

    public Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(NotFoundObjectException::new);
    }

    @Transactional
    public Article updateArticle(Long articleId, Contents contents, User user) {
        Article article = findArticle(articleId);
        Article newArticle = ArticleAssembler.toEntity(contents, user);
        article.update(newArticle);

        return article;
    }

    public void deleteArticle(Long articleId, User user) {
        Article article = findArticle(articleId);
        article.checkCorrespondingAuthor(user);
        articleRepository.deleteById(articleId);
    }

    public List<Comment> findAllComments(Article article) {
        return article.getComments();
    }

    public List<ResponseCommentDto> findAllComments(Long articleId) {
        List<ResponseCommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = articleRepository.findById(articleId)
                .orElseThrow(NotFoundObjectException::new).getComments();

        for(Comment comment : comments) {
            commentDtos.add(CommentAssembler.toResponseDto(comment));
        }

        return commentDtos;
    }

    @Transactional
    public Article addComment(Long articleId, Comment comment) {
        Article article = articleRepository.findById(articleId).orElseThrow(NotFoundObjectException::new);
        article.addComment(comment);
        return article;
    }

    public void checkAvailableUpdateUser(Article article, User user) {
        article.checkCorrespondingAuthor(user);
    }
}
