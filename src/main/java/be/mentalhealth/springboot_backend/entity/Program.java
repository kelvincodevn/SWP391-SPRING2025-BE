package be.mentalhealth.springboot_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "programs")
public class Program {
    @Id
    @Column(name = "id")
    private Long programID;


    private String programName;


    private String programCategory;


    private String programDescription;

    public boolean isDeleted = false;

    public Program() {
    }

    public Program(Long programId, String programName, String programCategory, String programDescription) {
        this.programID = programId;
        this.programName = programName;
        this.programCategory = programCategory;
        this.programDescription = programDescription;
    }

    public Long getProgramId() {
        return programID;
    }

    public void setProgramId(Long programId) {
        this.programID = programId;
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
}
