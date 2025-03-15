package be.mentalhealth.springboot_backend.DTO;

public class ProgramDTO {
    private String programName;
    private String programCategory;
    private String programDescription;
    private boolean isDeleted;

    // Constructors
    public ProgramDTO() {}

    public ProgramDTO(String programName, String programCategory, String programDescription, boolean isDeleted) {
        this.programName = programName;
        this.programCategory = programCategory;
        this.programDescription = programDescription;
        this.isDeleted = isDeleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
