

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String content;
    private Double star;
    private String reply;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 사용자 정보
    private Long userId;
    private String userName;

    // 가게 정보
    private Long restaurantId;
    private String restaurantName;

    // 이미지 정보
    private List<String> imageUrls;

    // Entity -> DTO 변환
    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .content(review.getContent())
                .star(review.getStar())
                .reply(review.getReply())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .userId(review.getUser() != null ? review.getUser().getId() : null)
                .userName(review.getUser() != null ? review.getUser().getName() : "익명")
                .restaurantId(review.getRestaurant() != null ? review.getRestaurant().getId() : null)
                .restaurantName(review.getRestaurant() != null ? review.getRestaurant().getName() : null)
                .imageUrls(review.getImages() != null ?
                        review.getImages().stream()
                                .map(image -> image.getImageUrl())
                                .collect(Collectors.toList()) : null)
                .build();
    }
}