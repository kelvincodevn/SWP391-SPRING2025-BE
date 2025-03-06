package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    private String testsName;
    private String testsDescription;
    private List<SetOfQuestionsDTO> questions;
}
