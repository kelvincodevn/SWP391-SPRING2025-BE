package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PsychologistDetailDTO {
    private String major;
    private String workplace;
    private String degree;

    public PsychologistDetailDTO() {
    }

    public PsychologistDetailDTO(String major, String workplace, String degree) {
        this.major = major;
        this.workplace = workplace;
        this.degree = degree;
    }


    private PsychologistDetailDTO(PsychologistDetailDTOBuilder builder) {
        this.major = builder.major;
        this.workplace = builder.workplace;
        this.degree = builder.degree;
    }

    public static class PsychologistDetailDTOBuilder {
        private String major;
        private String workplace;
        private String degree;

        public PsychologistDetailDTOBuilder() {}

        public PsychologistDetailDTOBuilder major(String major) {
            this.major = major;
            return this;
        }

        public PsychologistDetailDTOBuilder workplace(String workplace) {
            this.workplace = workplace;
            return this;
        }

        public PsychologistDetailDTOBuilder degree(String degree) {
            this.degree = degree;
            return this;
        }

        // Method build() để tạo đối tượng PsychologistDetailDTO
        public PsychologistDetailDTO build() {
            return new PsychologistDetailDTO(this);
        }
    }

    // Hàm khởi tạo Builder
    public static PsychologistDetailDTOBuilder builder() {
        return new PsychologistDetailDTOBuilder();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
