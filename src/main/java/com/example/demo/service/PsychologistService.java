package com.example.demo.service;

import com.example.demo.DTO.CreatePsychologistDTO;
import com.example.demo.DTO.PsychologistDTO;
import com.example.demo.DTO.PsychologistDetailDTO;
import com.example.demo.Repository.PsychologistDetailRepository;
import com.example.demo.Repository.PsychologistRepository;
import com.example.demo.entity.Psychologist;
import com.example.demo.entity.PsychologistDetail;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PsychologistService {
    private final PsychologistRepository psychologistRepository;

    private final PsychologistDetailRepository psychologistDetailRepository;

    public PsychologistService(PsychologistRepository psychologistRepository, PsychologistDetailRepository psychologistDetailRepository) {
        this.psychologistRepository = psychologistRepository;
        this.psychologistDetailRepository = psychologistDetailRepository;
    }

    public List<Psychologist> getAllPsychologists() {
        return psychologistRepository.findByStatusTrue();
    }

    public Psychologist getPsychologistById(Integer id) {
        return psychologistRepository.findById(id)
                .filter(Psychologist::getStatus)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
    }

    public Psychologist createPsychologist(CreatePsychologistDTO dto) {
        Psychologist psychologist = Psychologist.builder()
                .userName(dto.getUserName())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .status(true) // Mặc định active
                .createdDate(LocalDateTime.now())
                .build();

        return psychologistRepository.save(psychologist);
    }


    public Psychologist updatePsychologist(Integer id, PsychologistDTO psychologistDTO) {
        Psychologist psychologist = getPsychologistById(id);

        if (psychologistDTO.getUserName() != null) {
            psychologist.setUserName(psychologistDTO.getUserName());
        }
        if (psychologistDTO.getPassword() != null) {
            psychologist.setPassword(psychologistDTO.getPassword());
        }
        if (psychologistDTO.getFullName() != null) {
            psychologist.setFullName(psychologistDTO.getFullName());
        }
        if (psychologistDTO.getEmail() != null) {
            psychologist.setEmail(psychologistDTO.getEmail());
        }
        if (psychologistDTO.getPhone() != null) {
            psychologist.setPhone(psychologistDTO.getPhone());
        }
        if (psychologistDTO.getDob() != null) {
            psychologist.setDob(psychologistDTO.getDob());
        }
        if (psychologistDTO.getGender() != null) {
            psychologist.setGender(psychologistDTO.getGender());
        }
        if (psychologistDTO.getAvatar() != null) {
            psychologist.setAvatar(psychologistDTO.getAvatar());
        }
        if (psychologistDTO.getServiceFee() != null) {
            psychologist.setServiceFee(psychologistDTO.getServiceFee());
        }

        // Cập nhật psychologistDetail nếu có
        if (psychologistDTO.getPsychologistDetail() != null) {
            if (psychologist.getPsychologistDetail() == null) {
                psychologist.setPsychologistDetail(new PsychologistDetail());
                psychologist.getPsychologistDetail().setPsychologist(psychologist);
            }

            PsychologistDetail detail = psychologist.getPsychologistDetail();

            if (psychologistDTO.getPsychologistDetail().getMajor() != null) {
                detail.setMajor(psychologistDTO.getPsychologistDetail().getMajor());
            }
            if (psychologistDTO.getPsychologistDetail().getWorkplace() != null) {
                detail.setWorkplace(psychologistDTO.getPsychologistDetail().getWorkplace());
            }
            if (psychologistDTO.getPsychologistDetail().getDegree() != null) {
                detail.setDegree(psychologistDTO.getPsychologistDetail().getDegree());
            }
        }

        return psychologistRepository.save(psychologist);
    }

    public void softDeletePsychologist(Integer id) {
        Psychologist psychologist = getPsychologistById(id);
        psychologist.setStatus(false);
        psychologistRepository.save(psychologist);
    }
    public PsychologistDetailDTO getPsychologistDetailOnly(Integer id) {
        PsychologistDetail detail = psychologistDetailRepository.findByPsychologist_PsychoId(id)
                .orElseThrow(() -> new RuntimeException("Psychologist detail not found"));

        return PsychologistDetailDTO.builder()
                .major(detail.getMajor())
                .workplace(detail.getWorkplace())
                .degree(detail.getDegree())
                .build();
    }

}
