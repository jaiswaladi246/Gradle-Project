package com.devopsshack.gradleboard.model;

import java.time.LocalDateTime;

public record BuildRecord(
        long id,
        String serviceName,
        String branch,
        String environment,
        String commitId,
        BuildStatus status,
        int durationSeconds,
        LocalDateTime createdAt) {
}
