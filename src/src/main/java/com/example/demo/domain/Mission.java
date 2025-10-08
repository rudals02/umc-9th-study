package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mission extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "missions_seq")
    @SequenceGenerator(name = "missions_seq", sequenceName = "missions_seq", allocationSize = 1)
    private Long missionId;
    
    @Column(length = 100)
    private String missionName;
    
    @Column(length = 500)
    private String description;
    
    private Integer points;
    
    // N:M 관계 - Mission : User (through MyMission)
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MyMission> userMissions = new ArrayList<>();
    
    // N:M 관계 - Mission : User (through MissionStatus)
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionStatus> missionStatuses = new ArrayList<>();
    
    // 1:N 관계 - Mission : MissionReview
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionReview> reviews = new ArrayList<>();
    
    // N:M 관계 - Mission : Market (through MissionMarket)
    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionMarket> missionMarkets = new ArrayList<>();
}
