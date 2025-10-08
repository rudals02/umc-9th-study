package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "agreement")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agreement {
    
    @Id
    private Long userId;
    
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean over15;
    
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean termsOfService;
    
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean locationService;
    
    @Column(columnDefinition = "NUMBER(1) DEFAULT 0")
    private Boolean marketing;
}
