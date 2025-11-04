

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewSearchCondition {
    // 필터링 조건
    private Long restaurantId;  // 특정 가게의 리뷰만 조회
    private Double star;        // 특정 별점의 리뷰만 조회
    private Double minStar;     // 최소 별점 (예: 4점 이상)
    private Double maxStar;     // 최대 별점 (예: 3점 이하)

    // 추가 필터링 옵션
    private String keyword;     // 리뷰 내용 검색
    private Boolean hasReply;   // 답글이 있는 리뷰만 조회
}