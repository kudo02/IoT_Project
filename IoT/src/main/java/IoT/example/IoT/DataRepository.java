package IoT.example.IoT;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {

//    List<Data> getAll();

    Optional<Data> findTopByOrderByTimestampDesc();

    List<Data> findTop7ByOrderByTimestampDesc();

    Page<Data> findAll(Pageable pageable);

//    @Query("SELECT d FROM Data d WHERE d.timestamp BETWEEN :startTime AND :endTime")
//    List<Data> getDataBetweenTimeStamps(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Query("SELECT d FROM Data d WHERE d.timestamp BETWEEN :startDate AND :endDate")
    List<Data> findByTimeStampBetween(Date startDate, Date endDate);
}
