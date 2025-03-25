package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.Repository.TestHistoryRepository;
import be.mentalhealth.springboot_backend.Repository.UserRepository;
import be.mentalhealth.springboot_backend.enums.RoleEnum;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {
    private final TestHistoryRepository testHistoryRepository;
    private final UserRepository userRepository;

    public DashboardService(TestHistoryRepository testHistoryRepository, UserRepository userRepository) {
        this.testHistoryRepository = testHistoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Count users who are either STUDENT or PARENT.
     */
    public long countEligibleUsers() {
        return userRepository.countByRoleEnumIn(List.of(RoleEnum.STUDENT, RoleEnum.PARENT));
    }

    /**
     * Count test histories by test name.
     */
    public long countTestHistoriesByTestName(String testName) {
        return testHistoryRepository.countByTestResult_Test_TestsName(testName);
    }
}

