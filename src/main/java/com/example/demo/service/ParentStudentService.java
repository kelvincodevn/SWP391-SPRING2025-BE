//package com.example.demo.service;
//
//import com.example.demo.Repository.ParentStudentRepository;
//import com.example.demo.Repository.UserRepository;
//import com.example.demo.entity.ParentStudent;
//import com.example.demo.entity.ParentStudentId;
//import com.example.demo.entity.User;
//import com.example.demo.enums.AssociateStatus;
//import com.example.demo.enums.RoleEnum;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ParentStudentService {
//
//    @Autowired
//    private ParentStudentRepository parentStudentRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Tìm kiếm học sinh theo email
//    public User findStudentByEmail(String email) {
//        return userRepository.findByEmailAndIsDeletedFalse(email)
//                .filter(user -> user.getRoleEnum() == RoleEnum.STUDENT)
//                .orElseThrow(() -> new IllegalArgumentException("Student not found with email: " + email));
//    }
//
//    // Gửi yêu cầu liên kết từ phụ huynh
//    public ParentStudent sendLinkRequest(Long parentId, Long studentId) {
//        User parent = userRepository.findById(parentId)
//                .orElseThrow(() -> new IllegalArgumentException("Parent not found with ID: " + parentId));
//        User student = userRepository.findById(studentId)
//                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
//
//        if (parent.getRoleEnum() != RoleEnum.PARENT) {
//            throw new IllegalArgumentException("User with ID " + parentId + " is not a parent.");
//        }
//        if (student.getRoleEnum() != RoleEnum.STUDENT) {
//            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
//        }
//
//        // Kiểm tra xem yêu cầu đã tồn tại chưa
//        ParentStudentId id = new ParentStudentId(parentId, studentId);
//        if (parentStudentRepository.findById(id).isPresent()) {
//            throw new IllegalArgumentException("A link request already exists between this parent and student.");
//        }
//
//        ParentStudent parentStudent = new ParentStudent();
//        parentStudent.setParentId(parentId);
//        parentStudent.setStudentId(studentId);
//        parentStudent.setParent(parent);
//        parentStudent.setStudent(student);
//        parentStudent.setAssociateStatus(AssociateStatus.PENDING);
//
//        return parentStudentRepository.save(parentStudent);
//    }
//
//    // Lấy danh sách yêu cầu liên kết cho học sinh
//    public List<ParentStudent> getLinkRequestsForStudent(Long studentId) {
//        User student = userRepository.findById(studentId)
//                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
//        if (student.getRoleEnum() != RoleEnum.STUDENT) {
//            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
//        }
//
//        return parentStudentRepository.findByStudent(student).stream()
//                .filter(ps -> ps.getAssociateStatus() == AssociateStatus.PENDING)
//                .filter(ps -> ps.getExpirationDate().isAfter(LocalDateTime.now())) // Chỉ lấy yêu cầu chưa hết hạn
//                .collect(Collectors.toList());
//    }
//
//    // Học sinh xác nhận hoặc từ chối yêu cầu liên kết
//    public ParentStudent respondToLinkRequest(Long parentId, Long studentId, boolean confirm) {
//        ParentStudentId id = new ParentStudentId(parentId, studentId);
//        ParentStudent parentStudent = parentStudentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Link request not found."));
//
//        if (parentStudent.getAssociateStatus() != AssociateStatus.PENDING) {
//            throw new IllegalArgumentException("This request has already been processed.");
//        }
//
//        if (parentStudent.getExpirationDate().isBefore(LocalDateTime.now())) {
//            parentStudent.setAssociateStatus(AssociateStatus.EXPIRED);
//            parentStudentRepository.save(parentStudent);
//            throw new IllegalArgumentException("This request has expired.");
//        }
//
//        parentStudent.setAssociateStatus(confirm ? AssociateStatus.CONFIRMED : AssociateStatus.DECLINED);
//        return parentStudentRepository.save(parentStudent);
//    }
//
//    // Lấy danh sách học sinh mà phụ huynh đã liên kết (trạng thái CONFIRMED)
//    public List<User> getStudentsByParent(Long parentId) {
//        User parent = userRepository.findById(parentId)
//                .orElseThrow(() -> new IllegalArgumentException("Parent not found with ID: " + parentId));
//        if (parent.getRoleEnum() != RoleEnum.PARENT) {
//            throw new IllegalArgumentException("User with ID " + parentId + " is not a parent.");
//        }
//
//        return parentStudentRepository.findByParent(parent).stream()
//                .filter(ps -> ps.getAssociateStatus() == AssociateStatus.CONFIRMED)
//                .map(ParentStudent::getStudent)
//                .collect(Collectors.toList());
//    }
//}

package com.example.demo.service;

import com.example.demo.Repository.ParentStudentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.ParentStudent;
import com.example.demo.entity.ParentStudentId;
import com.example.demo.entity.User;
import com.example.demo.enums.AssociateStatus;
import com.example.demo.enums.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentStudentService {

    @Autowired
    private ParentStudentRepository parentStudentRepository;

    @Autowired
    private UserRepository userRepository;

    // Tìm kiếm học sinh theo email
//    public User findStudentByEmail(String email) {
//        return userRepository.findByEmailAndIsDeletedFalse(email)
//                .filter(user -> user.getRoleEnum() == RoleEnum.STUDENT)
//                .orElseThrow(() -> new IllegalArgumentException("Student not found with email: " + email));
//    }

    public User findStudentByEmail(String email) {
        User student = userRepository.findByEmailAndIsDeletedFalse(email)
                .filter(user -> user.getRoleEnum() == RoleEnum.STUDENT)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with email: " + email));

        // Kiểm tra trạng thái liên kết của học sinh
        List<ParentStudent> associations = parentStudentRepository.findByStudent(student);
        for (ParentStudent association : associations) {
            if (association.getAssociateStatus() == AssociateStatus.PENDING) {
                throw new IllegalArgumentException("This student already has a pending link request from another parent.");
            }
            if (association.getAssociateStatus() == AssociateStatus.CONFIRMED) {
                throw new IllegalArgumentException("This student is already linked to a parent.");
            }
        }

        return student;
    }

    // Gửi yêu cầu liên kết từ phụ huynh
//    public ParentStudent sendLinkRequest(Long parentId, Long studentId) {
//        User parent = userRepository.findById(parentId)
//                .orElseThrow(() -> new IllegalArgumentException("Parent not found with ID: " + parentId));
//        User student = userRepository.findById(studentId)
//                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
//
//        if (parent.getRoleEnum() != RoleEnum.PARENT) {
//            throw new IllegalArgumentException("User with ID " + parentId + " is not a parent.");
//        }
//        if (student.getRoleEnum() != RoleEnum.STUDENT) {
//            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
//        }
//
//        // Kiểm tra xem yêu cầu đã tồn tại chưa
//        ParentStudentId id = new ParentStudentId(parentId, studentId);
//        if (parentStudentRepository.findById(id).isPresent()) {
//            throw new IllegalArgumentException("A link request already exists between this parent and student.");
//        }
//
//        ParentStudent parentStudent = new ParentStudent();
//        parentStudent.setId(id);
//        parentStudent.setParent(parent);
//        parentStudent.setStudent(student);
//        parentStudent.setAssociateStatus(AssociateStatus.PENDING);
//
//        return parentStudentRepository.save(parentStudent);
//    }

    public ParentStudent sendLinkRequest(Long parentId, Long studentId) {
        User parent = userRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found with ID: " + parentId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));

        if (parent.getRoleEnum() != RoleEnum.PARENT) {
            throw new IllegalArgumentException("User with ID " + parentId + " is not a parent.");
        }
        if (student.getRoleEnum() != RoleEnum.STUDENT) {
            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
        }

        // Kiểm tra trạng thái liên kết của học sinh
        List<ParentStudent> associations = parentStudentRepository.findByStudent(student);
        for (ParentStudent association : associations) {
            if (association.getAssociateStatus() == AssociateStatus.PENDING) {
                throw new IllegalArgumentException("This student already has a pending link request from another parent.");
            }
            if (association.getAssociateStatus() == AssociateStatus.CONFIRMED) {
                throw new IllegalArgumentException("This student is already linked to a parent.");
            }
            // Kiểm tra nếu phụ huynh này đã bị DECLINED hoặc CANCELLED
            if (association.getParentId().equals(parentId) &&
                    (association.getAssociateStatus() == AssociateStatus.DECLINED ||
                            association.getAssociateStatus() == AssociateStatus.CANCELLED)) {
                throw new IllegalArgumentException("You cannot send a link request to this student because a previous request was declined or cancelled.");
            }
        }

        ParentStudentId id = new ParentStudentId(parentId, studentId);
        ParentStudent parentStudent = new ParentStudent();
        parentStudent.setId(id);
        parentStudent.setParent(parent);
        parentStudent.setStudent(student);
        parentStudent.setAssociateStatus(AssociateStatus.PENDING);

        return parentStudentRepository.save(parentStudent);
    }

    // Lấy danh sách yêu cầu liên kết cho học sinh
    public List<ParentStudent> getLinkRequestsForStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        if (student.getRoleEnum() != RoleEnum.STUDENT) {
            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
        }

        return parentStudentRepository.findByStudent(student).stream()
                .filter(ps -> ps.getAssociateStatus() == AssociateStatus.PENDING)
                .filter(ps -> ps.getExpirationDate().isAfter(LocalDateTime.now())) // Chỉ lấy yêu cầu chưa hết hạn
                .collect(Collectors.toList());
    }

    // Học sinh xác nhận hoặc từ chối yêu cầu liên kết
    public ParentStudent respondToLinkRequest(Long parentId, Long studentId, boolean confirm) {
        ParentStudentId id = new ParentStudentId(parentId, studentId);
        ParentStudent parentStudent = parentStudentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link request not found."));

        if (parentStudent.getAssociateStatus() != AssociateStatus.PENDING) {
            throw new IllegalArgumentException("This request has already been processed.");
        }

        if (parentStudent.getExpirationDate().isBefore(LocalDateTime.now())) {
            parentStudent.setAssociateStatus(AssociateStatus.EXPIRED);
            parentStudentRepository.save(parentStudent);
            throw new IllegalArgumentException("This request has expired.");
        }

        parentStudent.setAssociateStatus(confirm ? AssociateStatus.CONFIRMED : AssociateStatus.DECLINED);
        return parentStudentRepository.save(parentStudent);
    }

    // Lấy danh sách học sinh mà phụ huynh đã liên kết (trạng thái CONFIRMED)
    public List<User> getStudentsByParent(Long parentId) {
        User parent = userRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("Parent not found with ID: " + parentId));
        if (parent.getRoleEnum() != RoleEnum.PARENT) {
            throw new IllegalArgumentException("User with ID " + parentId + " is not a parent.");
        }

        return parentStudentRepository.findByParent(parent).stream()
                .filter(ps -> ps.getAssociateStatus() == AssociateStatus.CONFIRMED)
                .map(ParentStudent::getStudent)
                .collect(Collectors.toList());
    }

    public List<User> getParentsByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID: " + studentId));
        if (student.getRoleEnum() != RoleEnum.STUDENT) {
            throw new IllegalArgumentException("User with ID " + studentId + " is not a student.");
        }

        return parentStudentRepository.findByStudent(student).stream()
                .filter(ps -> ps.getAssociateStatus() == AssociateStatus.CONFIRMED)
                .map(ParentStudent::getParent)
                .collect(Collectors.toList());
    }

    // Trong ParentStudentService.java
    public ParentStudent cancelLink(Long parentId, Long studentId) {
        ParentStudentId id = new ParentStudentId(parentId, studentId);
        ParentStudent parentStudent = parentStudentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Link not found."));

        if (parentStudent.getAssociateStatus() != AssociateStatus.CONFIRMED) {
            throw new IllegalArgumentException("This link cannot be cancelled because it is not confirmed.");
        }

        parentStudent.setAssociateStatus(AssociateStatus.CANCELLED);
        return parentStudentRepository.save(parentStudent);
    }


}