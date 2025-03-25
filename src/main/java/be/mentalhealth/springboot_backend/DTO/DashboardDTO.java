package be.mentalhealth.springboot_backend.DTO;

import java.util.Map;

public class DashboardDTO {
    private long totalUsers;
    private Map<String, Long> testSummary;

    public DashboardDTO(long totalUsers, Map<String, Long> testSummary) {
        this.totalUsers = totalUsers;
        this.testSummary = testSummary;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Map<String, Long> getTestSummary() {
        return testSummary;
    }

    public void setTestSummary(Map<String, Long> testSummary) {
        this.testSummary = testSummary;
    }
}