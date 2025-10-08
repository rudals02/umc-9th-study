package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "market_information")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketInformation extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "market_seq")
    @SequenceGenerator(name = "market_seq", sequenceName = "market_seq", allocationSize = 1)
    private Long marketId;
    
    @Column(length = 255)
    private String address;
    
    @Column(length = 100)
    private String name;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 50)
    private String category;
    
    // N:M 관계 - Market : Mission (through MissionMarket)
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MissionMarket> missionMarkets = new ArrayList<>();
}
