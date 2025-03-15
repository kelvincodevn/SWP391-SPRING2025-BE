package be.mentalhealth.springboot_backend.DTO;

public class ProgramViewDTO {

    private String programName;
    private String programCategory;
    private String programDescription;

    // Constructors
    public ProgramViewDTO() {}

    public ProgramViewDTO(String programName, String programCategory, String programDescription) {
        this.programName = programName;
        this.programCategory = programCategory;
        this.programDescription = programDescription;
    }

    // Getters and Setters
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramCategory() {
        return programCategory;
    }

    public void setProgramCategory(String programCategory) {
        this.programCategory = programCategory;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

}
