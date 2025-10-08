package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_locations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLocation {
    
    @Id
    private Long userId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(length = 50)
    private String districts;
    
    private LocalDateTime updatedAt;
}
