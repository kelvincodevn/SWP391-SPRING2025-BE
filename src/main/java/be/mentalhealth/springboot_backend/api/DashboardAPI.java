package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.DTO.DashboardDTO;
import be.mentalhealth.springboot_backend.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Manager/dashboard")
@SecurityRequirement(name = "api")
public class DashboardAPI {
    private  DashboardService dashboardService;

    public DashboardAPI(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ✅ Get total count of eligible users (STUDENT & PARENT)
    @GetMapping("/users/count")
    public ResponseEntity<Long> getEligibleUsersCount() {
        long count = dashboardService.countEligibleUsers();
        return ResponseEntity.ok(count);
    }

    // ✅ Get test history count by test name
    @GetMapping("/test-histories/count")
    public ResponseEntity<Long> getTestHistoriesCount(@RequestParam String testName) {
        long count = dashboardService.countTestHistoriesByTestName(testName);
        return ResponseEntity.ok(count);
    }
}