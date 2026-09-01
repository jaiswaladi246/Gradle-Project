package com.devopsshack.gradleboard.service;

import com.devopsshack.gradleboard.model.BuildRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuildServiceTest {

    @Test
    void triggerBuildAddsANewBuild() {
        BuildService service = new BuildService();
        long before = service.summary().total();

        BuildRecord build = service.triggerBuild("billing-api", "feature/payments", "QA");

        assertEquals(before + 1, service.summary().total());
        assertEquals("billing-api", build.serviceName());
        assertEquals("feature/payments", build.branch());
        assertEquals("QA", build.environment());
        assertNotNull(build.status());
        assertEquals(7, build.commitId().length());
    }

    @Test
    void summaryContainsSeededBuilds() {
        BuildService service = new BuildService();

        assertTrue(service.summary().total() >= 8);
        assertTrue(service.summary().successRate() >= 0);
        assertTrue(service.summary().successRate() <= 100);
    }
}
