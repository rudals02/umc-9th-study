import com.example.demo.dto.AvailableMissionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyMissionRepository extends JpaRepository<MyMission, Long> {


    @Query("""
        SELECT new com.example.dto.AvailableMissionDto(
            mi.marketId,
            mi.name,
            mi.address,
            m.mission.id,
            m.status,
            m.startTime,
            m.completedAt,
            ms.earnedPoints
        )
        FROM MyMission m
        JOIN MarketInformation mi ON m.mission.id = mi.marketId
        JOIN UserLocation ul ON m.user.id = ul.user.id
        LEFT JOIN MissionStatus ms ON m.mission.id = ms.mission.id 
            AND m.user.id = ms.user.id
        WHERE ul.user.id = :userId
            AND ul.districts = :district
        ORDER BY m.startTime DESC
        """)
    Page<AvailableMissionDto> findAvailableMissionsByLocation(
            @Param("userId") Long userId,
            @Param("district") String district,
            Pageable pageable
    );