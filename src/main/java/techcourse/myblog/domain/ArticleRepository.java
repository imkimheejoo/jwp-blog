package techcourse.myblog.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ArticleRepository extends CrudRepository<Article,Long> {
}
