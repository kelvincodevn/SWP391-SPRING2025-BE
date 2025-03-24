package be.mentalhealth.springboot_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programID;


    private String programName;


    private String programCategory;


    private String programDescription;

    private String programLink;

    public boolean isDeleted = false;

    public Program() {
    }

    public Program(Long programID, String programName, String programCategory, String programDescription, String programLink, boolean isDeleted) {
        this.programID = programID;
        this.programName = programName;
        this.programCategory = programCategory;
        this.programDescription = programDescription;
        this.programLink = programLink;
        this.isDeleted = isDeleted;
    }

    public String getProgramLink() {
        return this.programLink;
    }

    public void setProgramLink(String programLink) {
        this.programLink = programLink;
    }

    public Long getProgramId() {
        return programID;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
