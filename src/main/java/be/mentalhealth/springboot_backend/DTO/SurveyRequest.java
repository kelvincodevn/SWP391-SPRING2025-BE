package be.mentalhealth.springboot_backend.DTO;

import lombok.Data;
<<<<<<< HEAD

=======
>>>>>>> 5982d663ddbb7d04b583f75c1c58e950d57db4e7
import java.util.List;

@Data
public class SurveyRequest {
    private String title;
    private String description;
    private String surveyLink;
    private List<String> emails; // Danh sách email người nhận survey
}
