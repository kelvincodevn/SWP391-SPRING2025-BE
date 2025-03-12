package be.mentalhealth.springboot_backend.service;

import be.mentalhealth.springboot_backend.DTO.BookingRequest;
import be.mentalhealth.springboot_backend.DTO.BookingResponse;
import be.mentalhealth.springboot_backend.entity.Booking;
import be.mentalhealth.springboot_backend.entity.PsychologistSlot;
import be.mentalhealth.springboot_backend.entity.Slot;
import be.mentalhealth.springboot_backend.enums.AvailabilityStatus;
import be.mentalhealth.springboot_backend.enums.BookingStatus;
import be.mentalhealth.springboot_backend.repository.BookingRepository;
import be.mentalhealth.springboot_backend.repository.PsychologistSlotRepository;
import be.mentalhealth.springboot_backend.repository.SlotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service

public class BookingService {
    public BookingService(BookingRepository bookingRepository, EmailBookingService emailBookingService, SlotRepository slotRepository, PsychologistSlotRepository psychologistSlotRepository) {
        this.bookingRepository = bookingRepository;
        this.emailBookingService = emailBookingService;
        this.slotRepository = slotRepository;
        this.psychologistSlotRepository = psychologistSlotRepository;
    }

    private final BookingRepository bookingRepository;
    private final EmailBookingService emailBookingService;
    private final SlotRepository slotRepository;
    private final PsychologistSlotRepository psychologistSlotRepository;

    public BookingResponse createBooking(BookingRequest request) {
        // Kiểm tra xem slot có tồn tại và có sẵn không
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot không tồn tại"));

        if (!slot.isAvailable()) {
            throw new RuntimeException("Slot này đã được đặt");
        }

        // Kiểm tra psychologistSlot có tồn tại không
        PsychologistSlot psychologistSlot = psychologistSlotRepository.findBySlot(slot)
                .orElseThrow(() -> new RuntimeException("PsychologistSlot không tồn tại!"));

        if (!psychologistSlot.getAvailabilityStatus().equals(AvailabilityStatus.AVAILABLE)) {
            throw new RuntimeException("Slot này đã được đặt bởi người khác!");
        }

        // Tạo booking mới
        Booking booking = new Booking();
        booking.setSlot(slot);
        booking.setFullName(request.getFullName());
        booking.setGender(request.getGender());
        booking.setEmail(request.getEmail());
        booking.setPhoneNumber(request.getPhoneNumber());
        booking.setDob(request.getDob());
        booking.setReason(request.getReason());
        booking.setFee(request.getFee());
        booking.setStatus(BookingStatus.PENDING);

        // Lưu vào DB
        bookingRepository.save(booking);

        // Cập nhật trạng thái slot
        slot.setAvailable(false);
        slotRepository.save(slot);

        // Cập nhật trạng thái psychologistSlot
        psychologistSlot.setAvailabilityStatus(AvailabilityStatus.BOOKED);
        psychologistSlotRepository.save(psychologistSlot);

        return new BookingResponse(booking.getId(), "Pending", "Booking request created successfully.");
    }

    public void acceptBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        booking.setStatus(BookingStatus.ACCEPTED);
        bookingRepository.save(booking);

        String confirmLink = "http://localhost:8080/api/bookings/" + bookingId + "/confirm";
        String emailBody = "Your booking has been accepted. Click to confirm: " + confirmLink;

        try {
            emailBookingService.sendEmail(booking.getEmail(), "Booking Confirmation", emailBody);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public void declineBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        booking.setStatus(BookingStatus.DECLINED);
        bookingRepository.save(booking);

        PsychologistSlot psychologistSlot = psychologistSlotRepository.findBySlot(booking.getSlot())
                .orElseThrow(() -> new RuntimeException("PsychologistSlot không tồn tại!"));

        psychologistSlot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        psychologistSlotRepository.save(psychologistSlot);

        Slot slot = booking.getSlot();
        slot.setAvailable(true);
        slotRepository.save(slot);

        String emailBody = "Your booking has been declined. Please try another slot.";
        try {
            emailBookingService.sendEmail(booking.getEmail(), "Booking Declined", emailBody);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public void confirmBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        String emailBody = "Your booking has been confirmed successfully.";
        try {
            emailBookingService.sendEmail(booking.getEmail(), "Booking Confirmed", emailBody);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    private Booking getBookingOrThrow(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại!"));
    }

    public void sendMedicalReport(int bookingId, MultipartFile file) {
        Booking booking = getBookingOrThrow(bookingId);

        String emailBody = "Dear " + booking.getFullName() + ",\n\nYour medical report is attached below.";

        emailBookingService.sendEmailWithAttachment(
                booking.getEmail(),
                "Medical Report - Booking #" + bookingId,
                emailBody,
                file
        );

        // Cập nhật trạng thái booking thành "Completed"
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setMedicalReportPath(file.getOriginalFilename());
        bookingRepository.save(booking);
    }
}

