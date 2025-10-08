package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "user_preferences")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference extends BaseEntity {
    
    @Id
    private Long userId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
}
