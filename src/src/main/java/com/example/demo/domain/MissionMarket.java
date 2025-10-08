package com.example.demo.domain;

import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "mission_markets")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MissionMarketId.class)
public class MissionMarket {
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private MarketInformation market;
    
    private LocalDateTime createdAt;
}

// Composite Key Class
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class MissionMarketId implements Serializable {
    private Long mission;
    private Long market;
}
