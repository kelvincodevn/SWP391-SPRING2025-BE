//package com.example.demo.entity;
//
//import com.example.demo.enums.AssociateStatus;
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "parent_student")
//@IdClass(ParentStudentId.class)
//public class ParentStudent {
//
//    @Id
//    @Column(name = "ParentID")
//    private Long parentId;
//
//    @Id
//    @Column(name = "StudentID")
//    private Long studentId;
//
//    @ManyToOne
//    @JoinColumn(name = "ParentID", referencedColumnName = "userID", insertable = false, updatable = false)
//    private User parent;
//
//    @ManyToOne
//    @JoinColumn(name = "StudentID", referencedColumnName = "userID", insertable = false, updatable = false)
//    private User student;
//
//    @Column(name = "CreatedDate")
//    private LocalDateTime createdDate;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "AssociateStatus", nullable = false)
//    private AssociateStatus associateStatus;
//
//    @Column(name = "ExpirationDate")
//    private LocalDateTime expirationDate; // Thời gian hết hạn của yêu cầu
//
//    @PrePersist
//    protected void onCreate() {
//        this.createdDate = LocalDateTime.now();
//        this.expirationDate = this.createdDate.plusDays(7); // Yêu cầu hết hạn sau 7 ngày
//        this.associateStatus = AssociateStatus.PENDING; // Mặc định là PENDING khi tạo mới
//    }
//
//    // Getters và Setters
//    public Long getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(Long parentId) {
//        this.parentId = parentId;
//    }
//
//    public Long getStudentId() {
//        return studentId;
//    }
//
//    public void setStudentId(Long studentId) {
//        this.studentId = studentId;
//    }
//
//    public User getParent() {
//        return parent;
//    }
//
//    public void setParent(User parent) {
//        this.parent = parent;
//        this.parentId = parent != null ? parent.getUserID() : null;
//    }
//
//    public User getStudent() {
//        return student;
//    }
//
//    public void setStudent(User student) {
//        this.student = student;
//        this.studentId = student != null ? student.getUserID() : null;
//    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public AssociateStatus getAssociateStatus() {
//        return associateStatus;
//    }
//
//    public void setAssociateStatus(AssociateStatus associateStatus) {
//        this.associateStatus = associateStatus;
//    }
//
//    public LocalDateTime getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(LocalDateTime expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//}

package com.example.demo.entity;

import com.example.demo.enums.AssociateStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parent_student")
public class ParentStudent {

    @EmbeddedId
    private ParentStudentId id;

    @ManyToOne
    @JoinColumn(name = "ParentID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User parent;

    @ManyToOne
    @JoinColumn(name = "StudentID", referencedColumnName = "userID", insertable = false, updatable = false)
    private User student;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "AssociateStatus", nullable = false)
    private AssociateStatus associateStatus;

    @Column(name = "ExpirationDate")
    private LocalDateTime expirationDate; // Thời gian hết hạn của yêu cầu

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.expirationDate = this.createdDate.plusDays(7); // Yêu cầu hết hạn sau 7 ngày
        this.associateStatus = AssociateStatus.PENDING; // Mặc định là PENDING khi tạo mới
    }

    // Getters và Setters
    public ParentStudentId getId() {
        return id;
    }

    public void setId(ParentStudentId id) {
        this.id = id;
    }

    public User getParent() {
        return parent;
    }

    public void setParent(User parent) {
        this.parent = parent;
        if (parent != null && this.id != null) {
            this.id.setParentId(parent.getUserID());
        }
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
        if (student != null && this.id != null) {
            this.id.setStudentId(student.getUserID());
        }
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public AssociateStatus getAssociateStatus() {
        return associateStatus;
    }

    public void setAssociateStatus(AssociateStatus associateStatus) {
        this.associateStatus = associateStatus;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    // Thêm các phương thức tiện ích để truy cập parentId và studentId trực tiếp
    public Long getParentId() {
        return id != null ? id.getParentId() : null;
    }

    public void setParentId(Long parentId) {
        if (this.id == null) {
            this.id = new ParentStudentId();
        }
        this.id.setParentId(parentId);
    }

    public Long getStudentId() {
        return id != null ? id.getStudentId() : null;
    }

    public void setStudentId(Long studentId) {
        if (this.id == null) {
            this.id = new ParentStudentId();
        }
        this.id.setStudentId(studentId);
    }
}