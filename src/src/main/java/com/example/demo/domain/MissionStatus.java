package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "mission_status")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MissionStatusId.class)
public class MissionStatus {
    
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
    private StatusType status;
    
    private Integer earnedPoints;
    
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean successButtonPressed;
}

// Composite Key Class
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class MissionStatusId implements Serializable {
    private Long user;
    private Long mission;
}
