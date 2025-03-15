package be.mentalhealth.springboot_backend.DTO;

public class ProgramDetailDTO {
    private String programName;
    private String programCategory;
    private String programDescription;

    private String programLink;

    public ProgramDetailDTO() {
    }

    public ProgramDetailDTO(String programName, String programCategory, String programDescription, String programLink) {
        this.programName = programName;
        this.programCategory = programCategory;
        this.programDescription = programDescription;
        this.programLink = programLink;
    }

    public String getProgramLink() {
        return programLink;
    }

    public void setProgramLink(String programLink) {
        this.programLink = programLink;
    }

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

    // Getters and setters
}
