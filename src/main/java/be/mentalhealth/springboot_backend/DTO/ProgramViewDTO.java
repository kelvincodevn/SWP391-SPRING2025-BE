package be.mentalhealth.springboot_backend.DTO;

public class ProgramViewDTO {

    private Long programID;
    private String programName;
    private String programCategory;


    // Constructors
    public ProgramViewDTO() {

    }

    public ProgramViewDTO(Long programID, String programName, String programCategory) {
        this.programID = programID;
        this.programName = programName;
        this.programCategory = programCategory;
    }

    public Long getProgramID() {
        return programID;
    }

    public void setProgramID(Long programID) {
        this.programID = programID;
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


}
