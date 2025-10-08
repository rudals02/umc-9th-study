package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "mission_reviews")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionReview extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mission_reviews_seq")
    @SequenceGenerator(name = "mission_reviews_seq", sequenceName = "mission_reviews_seq", allocationSize = 1)
    private Long reviewId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;
    
    @Column(columnDefinition = "NUMBER(1) CHECK (rating BETWEEN 1 AND 5)")
    private Integer rating;
}
