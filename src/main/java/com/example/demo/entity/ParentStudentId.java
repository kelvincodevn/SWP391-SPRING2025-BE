//package com.example.demo.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class ParentStudentId implements Serializable {
//
//    private Long parent;
//    private Long student;
//
//    public ParentStudentId() {
//    }
//
//    public ParentStudentId(Long parent, Long student) {
//        this.parent = parent;
//        this.student = student;
//    }
//
//    public Long getParent() {
//        return parent;
//    }
//
//    public void setParent(Long parent) {
//        this.parent = parent;
//    }
//
//    public Long getStudent() {
//        return student;
//    }
//
//    public void setStudent(Long student) {
//        this.student = student;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ParentStudentId that = (ParentStudentId) o;
//        return Objects.equals(parent, that.parent) &&
//                Objects.equals(student, that.student);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(parent, student);
//    }
//}

package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParentStudentId implements Serializable {

    @Column(name = "ParentID")
    private Long parentId;

    @Column(name = "StudentID")
    private Long studentId;

    // Constructor mặc định (bắt buộc cho JPA)
    public ParentStudentId() {
    }

    // Constructor với tham số (tiện lợi khi tạo instance)
    public ParentStudentId(Long parentId, Long studentId) {
        this.parentId = parentId;
        this.studentId = studentId;
    }

    // Getters và setters
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    // Triển khai equals() và hashCode() (bắt buộc cho khóa chính composite)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentStudentId that = (ParentStudentId) o;
        return Objects.equals(parentId, that.parentId) &&
                Objects.equals(studentId, that.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentId, studentId);
    }
}