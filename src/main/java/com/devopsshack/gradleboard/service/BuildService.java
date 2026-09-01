package com.devopsshack.gradleboard.service;

import com.devopsshack.gradleboard.model.BuildRecord;
import com.devopsshack.gradleboard.model.BuildStatus;
import com.devopsshack.gradleboard.model.BuildSummary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BuildService {

    private final List<BuildRecord> builds = new CopyOnWriteArrayList<>();
    private final AtomicLong sequence = new AtomicLong(2418);

    public BuildService() {
        seedDemoData();
    }

    public List<BuildRecord> findAll() {
        List<BuildRecord> snapshot = new ArrayList<>(builds);
        snapshot.sort(Comparator.comparing(BuildRecord::createdAt).reversed());
        return snapshot;
    }

    public BuildRecord triggerBuild(String serviceName, String branch, String environment) {
        String safeService = normalize(serviceName, "demo-service");
        String safeBranch = normalize(branch, "main");
        String safeEnvironment = normalize(environment, "DEV").toUpperCase(Locale.ROOT);

        long id = sequence.incrementAndGet();
        BuildStatus status = id % 6 == 0 ? BuildStatus.FAILED : BuildStatus.SUCCESS;
        int duration = 28 + (int) (id % 44);
        String commitId = generateCommitId(id, safeService);

        BuildRecord build = new BuildRecord(
                id,
                safeService,
                safeBranch,
                safeEnvironment,
                commitId,
                status,
                duration,
                LocalDateTime.now());

        builds.add(build);
        return build;
    }

    public BuildSummary summary() {
        List<BuildRecord> snapshot = findAll();
        long success = snapshot.stream().filter(b -> b.status() == BuildStatus.SUCCESS).count();
        long failed = snapshot.stream().filter(b -> b.status() == BuildStatus.FAILED).count();
        long running = snapshot.stream().filter(b -> b.status() == BuildStatus.RUNNING).count();
        long avgDuration = Math.round(snapshot.stream()
                .mapToInt(BuildRecord::durationSeconds)
                .average()
                .orElse(0));
        int successRate = snapshot.isEmpty() ? 0 : (int) Math.round(success * 100.0 / snapshot.size());

        return new BuildSummary(snapshot.size(), success, failed, running, avgDuration, successRate);
    }

    private String normalize(String value, String fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        return value.trim();
    }

    private String generateCommitId(long id, String serviceName) {
        String hash = Long.toHexString(Math.abs(id * 7_919L + serviceName.hashCode()));
        return (hash + "0000000").substring(0, 7).toLowerCase(Locale.ROOT);
    }

    private void seedDemoData() {
        LocalDateTime now = LocalDateTime.now();
        builds.add(new BuildRecord(2418, "payment-api", "main", "PROD", "7cd8a91", BuildStatus.SUCCESS, 46, now.minusMinutes(8)));
        builds.add(new BuildRecord(2417, "web-frontend", "feature/navbar", "DEV", "b81a209", BuildStatus.SUCCESS, 38, now.minusMinutes(19)));
        builds.add(new BuildRecord(2416, "auth-service", "main", "QA", "91e4bc3", BuildStatus.FAILED, 31, now.minusMinutes(34)));
        builds.add(new BuildRecord(2415, "orders-api", "release/3.2", "PPD", "e672bc1", BuildStatus.SUCCESS, 51, now.minusHours(1)));
        builds.add(new BuildRecord(2414, "notification-worker", "main", "DEV", "1f42a6c", BuildStatus.RUNNING, 18, now.minusHours(1).minusMinutes(18)));
        builds.add(new BuildRecord(2413, "inventory-api", "develop", "QA", "ff0d513", BuildStatus.SUCCESS, 42, now.minusHours(2)));
        builds.add(new BuildRecord(2412, "checkout-service", "main", "PROD", "5a11df8", BuildStatus.SUCCESS, 55, now.minusHours(3)));
        builds.add(new BuildRecord(2411, "reporting-job", "bugfix/export", "DEV", "d3f9940", BuildStatus.FAILED, 29, now.minusHours(4)));
    }
}
