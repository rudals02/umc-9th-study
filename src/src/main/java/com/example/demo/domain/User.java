package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    private Long id;
    
    @Column(length = 50)
    private String name;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 255)
    private String password;
    
    private LocalDate joinDate;
    
    private LocalDate birthDate;
    
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    // 1:1 관계 - User : UserLocation
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserLocation userLocation;
    
    // 1:1 관계 - User : Agreement
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Agreement agreement;
    
    // 1:1 관계 - User : UserPreference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserPreference userPreference;
    
    // 1:N 관계 - User : Notification
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();
    
    // N:M 관계 - User : Mission (through MyMission)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MyMission> myMissions = new ArrayList<>();
    
    // N:M 관계 - User : Mission (through MissionStatus)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionStatus> missionStatuses = new ArrayList<>();
    
    // 1:N 관계 - User : MissionReview
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionReview> missionReviews = new ArrayList<>();
}
