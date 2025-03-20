package com.example.demo.DTO;

public class ProgramDTO {
    private String programName;
    private String programCategory;
    private String programDescription;

    private String programLink;


    // Constructors
    public ProgramDTO() {}

    public ProgramDTO(String programName, String programCategory, String programDescription, String programLink) {
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
