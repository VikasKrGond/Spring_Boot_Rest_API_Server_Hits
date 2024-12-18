package com.jetbrains.apiserverhits.repository;

import com.jetbrains.apiserverhits.model.ApiMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApiMetricsRepository extends JpaRepository<ApiMetrics, String> {

    @Query("SELECT a.totalHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findTotalHitsByApiName(@Param("apiName") String apiName);

    @Query("SELECT a.successfulHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findSuccessfulHitsByApiName(@Param("apiName") String apiName);

    @Query("SELECT a.failedHits FROM ApiMetrics a WHERE a.apiName = :apiName")
    Integer findFailedHitsByApiName(@Param("apiName") String apiName);
}
