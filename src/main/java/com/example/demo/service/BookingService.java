package com.example.demo.service;

import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.BookingResponse;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.PsychologistSlotRepository;
import com.example.demo.Repository.SlotRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.entity.Booking;
import com.example.demo.entity.PsychologistSlot;
import com.example.demo.entity.Slot;
import com.example.demo.entity.User;
import com.example.demo.enums.AvailabilityStatus;
import com.example.demo.enums.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service

public class BookingService {
//    public BookingService(BookingRepository bookingRepository, EmailBookingService emailBookingService, SlotRepository slotRepository, PsychologistSlotRepository psychologistSlotRepository) {
//        this.bookingRepository = bookingRepository;
//        this.emailBookingService = emailBookingService;
//        this.slotRepository = slotRepository;
//        this.psychologistSlotRepository = psychologistSlotRepository;
//    }
//
//    private final BookingRepository bookingRepository;
//    private final EmailBookingService emailBookingService;
//    private final SlotRepository slotRepository;
//    private final PsychologistSlotRepository psychologistSlotRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    PsychologistSlotRepository psychologistSlotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailBookingService emailBookingService;

    public BookingResponse createBooking(Long userId, BookingRequest request) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        PsychologistSlot psychologistSlot = psychologistSlotRepository.findBySlot(slot)
                .orElseThrow(() -> new RuntimeException("PsychologistSlot not found"));

        if (!slot.isAvailable() || psychologistSlot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Slot is not available");
        }

        Booking booking = new Booking();
        booking.setSlot(slot);
        booking.setFullName(customer.getFullName());
        booking.setGender(customer.getGender());
        booking.setEmail(customer.getEmail());
        booking.setPhoneNumber(request.getPhoneNumber());
        booking.setDob(customer.getDob());
        booking.setReason(request.getReason());
        booking.setFee(50000.0); // Giả định phí mặc định, có thể lấy từ Psychologist
        booking.setStatus(BookingStatus.PENDING);
        bookingRepository.save(booking);

        slot.setAvailable(false);
        psychologistSlot.setAvailabilityStatus(AvailabilityStatus.BOOKED);
        slotRepository.save(slot);
        psychologistSlotRepository.save(psychologistSlot);

        emailBookingService.sendEmail(
                psychologistSlot.getPsychologist().getEmail(),
                "New Booking Request",
                "A customer has booked your slot on " + slot.getAvailableDate() + " from " + slot.getStartTime() + " to " + slot.getEndTime()
        );

        return new BookingResponse(booking.getId(), "PENDING", "Booking request created successfully");
    }

    public void acceptBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Can only accept PENDING bookings");
        }
        booking.setStatus(BookingStatus.ACCEPTED);
        bookingRepository.save(booking);

        String confirmLink = "http://localhost:8082/api/psychologist/bookings/" + bookingId + "/confirm";
        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Accepted",
                "Your booking has been accepted. Click to confirm: " + confirmLink
        );
    }

    public void declineBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Can only decline PENDING bookings");
        }
        booking.setStatus(BookingStatus.DECLINED);
        PsychologistSlot slot = psychologistSlotRepository.findBySlot(booking.getSlot())
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        slot.getSlot().setAvailable(true);
        bookingRepository.save(booking);
        psychologistSlotRepository.save(slot);
        slotRepository.save(slot.getSlot());

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Declined",
                "Your booking has been declined. Please try another slot."
        );
    }

    public void confirmBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.ACCEPTED) {
            throw new RuntimeException("Can only confirm ACCEPTED bookings");
        }
        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Confirmed",
                "Your booking has been confirmed. Please proceed to payment."
        );
    }

    public void completeBooking(Integer bookingId, MultipartFile report) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.PAID) {
            throw new RuntimeException("Can only complete PAID bookings");
        }
        String filePath = saveFile(report); // Logic lưu file, ví dụ dùng FileSystem hoặc S3
        booking.setStatus(BookingStatus.COMPLETED);
        booking.setMedicalReportPath(filePath);
        bookingRepository.save(booking);

        emailBookingService.sendEmailWithAttachment(
                booking.getEmail(),
                "Booking Completed - Report",
                "Dear " + booking.getFullName() + ",\n\nYour consultation report is attached.",
                report
        );
    }

    private String saveFile(MultipartFile file) {
        // Logic lưu file, trả về đường dẫn
        return "path/to/" + file.getOriginalFilename();
    }

    public void cancelBooking(Long userId, Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() == BookingStatus.PAID || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new RuntimeException("Cannot cancel a paid or completed booking");
        }
        PsychologistSlot slot = psychologistSlotRepository.findBySlot(booking.getSlot())
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        booking.setStatus(BookingStatus.CANCELLED);
        slot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        slot.getSlot().setAvailable(true);
        bookingRepository.save(booking);
        psychologistSlotRepository.save(slot);
        slotRepository.save(slot.getSlot());

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Cancelled",
                "You have successfully cancelled your booking."
        );
        emailBookingService.sendEmail(
                slot.getPsychologist().getEmail(),
                "Booking Cancelled",
                "The booking for your slot on " + slot.getSlot().getAvailableDate() + " has been cancelled."
        );
    }

    private Booking getBookingOrThrow(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}

