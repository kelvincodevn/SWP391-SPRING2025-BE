package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.model.Student;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// CRUD student
@RestController
@RequestMapping("/api/student")
public class StudentAPI {

    List<Student> students = new ArrayList<>();

    // Create
    // POST

    @PostMapping
    public ResponseEntity createStudent(@Valid @RequestBody Student student){
    students.add(student);
    return ResponseEntity.ok(student);
    }

    //GET List Student
    @GetMapping
    public ResponseEntity getAllStudent(){
    return ResponseEntity.ok(students);
    }

    //GET Specific Student
    @GetMapping("id")
    public int getStudentById(@PathVariable int id) {
        return id;
    }

    //UPDATE
    @PutMapping("id")
    public void updateStudent(){

    }

    //DELETE
    @DeleteMapping("id")
    public void deleteStudent(){

    }
}
