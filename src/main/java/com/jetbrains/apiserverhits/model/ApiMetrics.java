package com.jetbrains.apiserverhits.model;
//package com.example.apimetrics.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ApiMetrics {
    @Id
    private String apiName;
    private int totalHits;
    private int successfulHits;
    private int failedHits;

    // Getters and Setters
    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getSuccessfulHits() {
        return successfulHits;
    }

    public void setSuccessfulHits(int successfulHits) {
        this.successfulHits = successfulHits;
    }

    public int getFailedHits() {
        return failedHits;
    }

    public void setFailedHits(int failedHits) {
        this.failedHits = failedHits;
    }
}
