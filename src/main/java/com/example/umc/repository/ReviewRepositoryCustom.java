

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ReviewRepositoryCustom {
    Page<Review> searchReviews(ReviewSearchCondition condition, Pageable pageable);
}