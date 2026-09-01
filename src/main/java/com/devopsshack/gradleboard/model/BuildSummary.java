package com.devopsshack.gradleboard.model;

public record BuildSummary(
        long total,
        long successful,
        long failed,
        long running,
        long averageDurationSeconds,
        int successRate) {
}
