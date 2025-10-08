package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "my_missions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MyMissionId.class)
public class MyMission {
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private MyMissionStatus status;
    

    
    private LocalDateTime completedAt;
}

// Composite Key Class
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class MyMissionId implements Serializable {
    private Long user;
    private Long mission;
}
