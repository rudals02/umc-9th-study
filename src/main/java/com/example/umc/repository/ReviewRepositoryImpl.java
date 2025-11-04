package rto.intelfit.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Review> searchReviews(ReviewSearchCondition condition, Pageable pageable) {
        List<Review> content = queryFactory
                .selectFrom(review)
                .leftJoin(review.restaurant, restaurant).fetchJoin()
                .leftJoin(review.user, user).fetchJoin()
                .where(
                        restaurantIdEq(condition.getRestaurantId()),
                        starGoe(condition.getMinStar()),
                        starLoe(condition.getMaxStar()),
                        starEq(condition.getStar())
                )
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(review.count())
                .from(review)
                .where(
                        restaurantIdEq(condition.getRestaurantId()),
                        starGoe(condition.getMinStar()),
                        starLoe(condition.getMaxStar()),
                        starEq(condition.getStar())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression restaurantIdEq(Long restaurantId) {
        return restaurantId != null ? review.restaurant.id.eq(restaurantId) : null;
    }

    private BooleanExpression starEq(Double star) {
        return star != null ? review.star.eq(star) : null;
    }

    private BooleanExpression starGoe(Double minStar) {
        return minStar != null ? review.star.goe(minStar) : null;
    }

    private BooleanExpression starLoe(Double maxStar) {
        return maxStar != null ? review.star.loe(maxStar) : null;
    }
}