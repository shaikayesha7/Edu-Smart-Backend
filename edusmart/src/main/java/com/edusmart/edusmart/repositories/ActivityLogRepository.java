package com.edusmart.edusmart.repositories;

import com.edusmart.edusmart.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    // Fetch the 10 most recent logs
    List<ActivityLog> findTop10ByOrderByTimestampDesc();
}
