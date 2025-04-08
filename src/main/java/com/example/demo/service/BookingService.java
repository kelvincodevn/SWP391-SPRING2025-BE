//package com.example.demo.service;
//
//import com.example.demo.DTO.BookingRequest;
//import com.example.demo.DTO.BookingResponse;
//import com.example.demo.Repository.BookingRepository;
//import com.example.demo.Repository.SlotRepository;
//import com.example.demo.Repository.UserRepository;
//import com.example.demo.entity.Booking;
//import com.example.demo.entity.Slot;
//import com.example.demo.entity.User;
//import com.example.demo.enums.AvailabilityStatus;
//import com.example.demo.enums.BookingStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BookingService {
//    @Autowired
//    BookingRepository bookingRepository;
//
//    @Autowired
//    SlotRepository slotRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    EmailBookingService emailBookingService;
//
//    public BookingResponse createBooking(Long userId, BookingRequest request) {
//        User customer = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        Slot slot = slotRepository.findById(request.getSlotId())
//                .orElseThrow(() -> new RuntimeException("Slot not found"));
//
//        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
//            throw new RuntimeException("Slot is not available");
//        }
//
//        // Tạo booking
//        Booking booking = new Booking();
//        booking.setUser(customer);
//        booking.setSlot(slot);
//        booking.setFullName(request.getFullName() != null ? request.getFullName() : customer.getFullName());
//        booking.setGender(request.getGender() != null ? request.getGender() : customer.getGender());
//        booking.setEmail(request.getEmail() != null ? request.getEmail() : customer.getEmail());
//        booking.setPhoneNumber(request.getPhoneNumber());
//        booking.setDob(request.getDob() != null ? request.getDob() : customer.getDob());
//        booking.setReason(request.getReason());
//        // Lấy fee từ UserDetail của psychologist
//        Double fee = slot.getUser().getUserDetail().getFee();
//        booking.setFee(fee != null ? fee : 150000.0);
//        booking.setStatus(BookingStatus.PENDING);
//
//        // Lưu booking
//        booking = bookingRepository.save(booking);
//
//        // Cập nhật trạng thái Slot
//        slot.setAvailabilityStatus(AvailabilityStatus.BOOKED);
//        slotRepository.save(slot);
//
//        // Gửi email thông báo
//        emailBookingService.sendEmail(
//                slot.getUser().getEmail(),
//                "New Booking Request",
//                "A customer has booked your slot on " + slot.getAvailableDate() + " from " + slot.getStartTime() + " to " + slot.getEndTime()
//        );
//
//        return new BookingResponse(booking.getId(), "PENDING", "Booking request created successfully");
//    }
//
//    public void completeBooking(Integer bookingId, MultipartFile report) {
//        Booking booking = getBookingOrThrow(bookingId);
//        if (booking.getStatus() != BookingStatus.PAID) {
//            throw new RuntimeException("Can only complete PAID bookings");
//        }
//        String filePath = saveFile(report);
//        booking.setStatus(BookingStatus.COMPLETED);
//        booking.setMedicalReportPath(filePath);
//        bookingRepository.save(booking);
//
//        emailBookingService.sendEmailWithAttachment(
//                booking.getEmail(),
//                "Booking Completed - Report",
//                "Dear " + booking.getFullName() + ",\n\nYour consultation report is attached.",
//                report
//        );
//    }
//
//    private String saveFile(MultipartFile file) {
//        return "path/to/" + file.getOriginalFilename(); // Logic lưu file cần được triển khai thực tế
//    }
//
//    public void cancelBooking(Long userId, Integer bookingId) {
//        Booking booking = getBookingOrThrow(bookingId);
//        if (!booking.getUser().getUserID().equals(userId)) {
//            throw new RuntimeException("Unauthorized to cancel this booking");
//        }
//        if (booking.getStatus() == BookingStatus.PAID || booking.getStatus() == BookingStatus.COMPLETED) {
//            throw new RuntimeException("Cannot cancel a paid or completed booking");
//        }
//        Slot slot = booking.getSlot();
//        if (slot != null) {
//            booking.setStatus(BookingStatus.CANCELLED);
//            slot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
//            bookingRepository.save(booking);
//            slotRepository.save(slot);
//        }
//
//        emailBookingService.sendEmail(
//                booking.getEmail(),
//                "Booking Cancelled",
//                "You have successfully cancelled your booking."
//        );
//        emailBookingService.sendEmail(
//                slot.getUser().getEmail(),
//                "Booking Cancelled",
//                "The booking for your slot on " + slot.getAvailableDate() + " has been cancelled."
//        );
//    }
//
//    // Cập nhật phương thức getUserBookings
//    public List<BookingResponse> getUserBookings(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        List<Booking> bookings = bookingRepository.findByUser(user);
//
//        // Định dạng LocalDate thành String
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        return bookings.stream().map(booking -> {
//            Slot slot = booking.getSlot();
//            User psychologist = slot.getUser();
//            return new BookingResponse(
//                    booking.getId(),
//                    booking.getStatus().toString(),
//                    "Booking retrieved successfully",
//                    slot.getSlotId(),
//                    slot.getAvailableDate().format(dateFormatter), // Chuyển LocalDate thành String
//                    slot.getStartTime().toString(), // LocalTime tự động chuyển thành String
//                    slot.getEndTime().toString(),
//                    psychologist.getFullName(),
//                    booking.getFee()
//            );
//        }).collect(Collectors.toList());
//    }
//
//    // Thêm phương thức để lấy chi tiết booking
//    public BookingResponse getBookingDetails(Integer bookingId) {
//        Booking booking = getBookingOrThrow(bookingId);
//        Slot slot = booking.getSlot();
//        User psychologist = slot.getUser();
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        BookingResponse response = new BookingResponse(
//                booking.getId(),
//                booking.getStatus().toString(),
//                "Booking details retrieved successfully",
//                slot.getSlotId(),
//                slot.getAvailableDate().format(dateFormatter),
//                slot.getStartTime().toString(),
//                slot.getEndTime().toString(),
//                psychologist.getFullName(),
//                booking.getFee()
//        );
//
//        // Thêm thông tin chi tiết của psychologist
//        response.setPsychologistDetails(new BookingResponse.PsychologistDetails(
//                psychologist.getUserID(),
//                psychologist.getFullName(),
//                psychologist.getUserDetail().getMajor(),
//                psychologist.getUserDetail().getDegree(),
//                psychologist.getUserDetail().getWorkplace(),
//                psychologist.getUserDetail().getFee()
//        ));
//
//        return response;
//    }
//
//    // Thêm phương thức để lấy danh sách booking của psychologist
//    public List<BookingResponse> getPsychologistBookings(Long psychologistId) {
//        User psychologist = userRepository.findById(psychologistId)
//                .orElseThrow(() -> new RuntimeException("Psychologist not found"));
//
//        // Tìm tất cả slot của psychologist
//        List<Slot> slots = slotRepository.findByUser(psychologist);
//
//        // Tìm tất cả booking liên quan đến các slot này
//        List<Booking> bookings = bookingRepository.findBySlotIn(slots);
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//        return bookings.stream().map(booking -> {
//            Slot slot = booking.getSlot();
//            User client = booking.getUser();
//            BookingResponse response = new BookingResponse(
//                    booking.getId(),
//                    booking.getStatus().toString(),
//                    "Booking retrieved successfully",
//                    slot.getSlotId(),
//                    slot.getAvailableDate().format(dateFormatter),
//                    slot.getStartTime().toString(),
//                    slot.getEndTime().toString(),
//                    slot.getUser().getFullName(),
//                    booking.getFee()
//            );
//            // Thêm thông tin client (người đặt lịch)
//            response.setClientDetails(new BookingResponse.ClientDetails(
//                    client.getUserID(),
//                    client.getFullName(),
//                    client.getEmail()
//            ));
//            return response;
//        }).collect(Collectors.toList());
//    }
//
//    private Booking getBookingOrThrow(Integer bookingId) {
//        return bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new RuntimeException("Booking not found"));
//    }
//}

package com.example.demo.service;

import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.BookingResponse;
import com.example.demo.Repository.*;
import com.example.demo.entity.*;
import com.example.demo.enums.AvailabilityStatus;
import com.example.demo.enums.BookingStatus;
import com.example.demo.enums.RoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SlotRepository slotRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailBookingService emailBookingService;

    @Autowired
    TestScoringRepository testScoringRepository;

    @Autowired
    TestResultRepository testResultRepository;


    public BookingResponse createBooking(Long userId, BookingRequest request) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (slot.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Slot is not available");
        }

        // Tạo booking
        Booking booking = new Booking();
        booking.setUser(customer);
        booking.setSlot(slot);
        booking.setFullName(request.getFullName() != null ? request.getFullName() : customer.getFullName());
        booking.setGender(request.getGender() != null ? request.getGender() : customer.getGender());
        booking.setEmail(request.getEmail() != null ? request.getEmail() : customer.getEmail());
        booking.setPhoneNumber(request.getPhoneNumber());
        booking.setDob(request.getDob() != null ? request.getDob() : customer.getDob());
        booking.setReason(request.getReason());
        Double fee = slot.getUser().getUserDetail().getFee();
        booking.setFee(fee != null ? fee : 150000.0);
        booking.setStatus(BookingStatus.PENDING);

        // Lưu booking
        booking = bookingRepository.save(booking);

        // Cập nhật trạng thái Slot
        slot.setAvailabilityStatus(AvailabilityStatus.BOOKED);
        slotRepository.save(slot);

        // Gửi email thông báo
        emailBookingService.sendEmail(
                slot.getUser().getEmail(),
                "New Booking Request",
                "A customer has booked your slot on " + slot.getAvailableDate() + " from " + slot.getStartTime() + " to " + slot.getEndTime()
        );

        return new BookingResponse(booking.getId(), "PENDING", "Booking request created successfully");
    }

//    public void completeBooking(Integer bookingId, MultipartFile report) {
//        Booking booking = getBookingOrThrow(bookingId);
//        if (booking.getStatus() != BookingStatus.PAID) {
//            throw new RuntimeException("Can only complete PAID bookings");
//        }
//
//        Slot slot = booking.getSlot();
//        LocalDate currentDate = LocalDate.now();
//        LocalTime currentTime = LocalTime.now();
//
//        // Kiểm tra xem thời gian hiện tại có nằm trong slot không
//        if (!currentDate.equals(slot.getAvailableDate()) ||
//                currentTime.isBefore(slot.getStartTime()) ||
//                currentTime.isAfter(slot.getEndTime())) {
//            throw new RuntimeException("Booking can only be completed during the scheduled slot time");
//        }
//
//        String filePath = saveFile(report);
//        booking.setStatus(BookingStatus.COMPLETED);
//        booking.setMedicalReportPath(filePath);
//        bookingRepository.save(booking);
//
//        emailBookingService.sendEmailWithAttachment(
//                booking.getEmail(),
//                "Booking Completed - Report",
//                "Dear " + booking.getFullName() + ",\n\nYour consultation report is attached.",
//                report
//        );
//    }

//    public void completeBooking(Integer bookingId, MultipartFile report) {
//        Booking booking = getBookingOrThrow(bookingId);
//        if (booking.getStatus() != BookingStatus.PAID) {
//            throw new RuntimeException("Can only complete PAID bookings");
//        }
//
//        Slot slot = booking.getSlot();
//        LocalDate currentDate = LocalDate.now();
//        LocalTime currentTime = LocalTime.now();
//
//        // Kiểm tra thời gian slot
//        if (!currentDate.equals(slot.getAvailableDate()) ||
//                currentTime.isBefore(slot.getStartTime()) ||
//                currentTime.isAfter(slot.getEndTime())) {
//            throw new RuntimeException("Booking can only be completed during the scheduled slot time");
//        }
//
//        String filePath = saveFile(report);
//        booking.setStatus(BookingStatus.AWAITING_CONFIRMATION);
//        booking.setMedicalReportPath(filePath);
//        booking.setConfirmationDeadline(LocalDateTime.now().plusHours(24)); // Hết hạn sau 24 giờ
//        bookingRepository.save(booking);
//
//        // Gửi email yêu cầu xác nhận
//        emailBookingService.sendEmailWithAttachment(
//                booking.getEmail(),
//                "Booking Awaiting Your Confirmation",
//                "Dear " + booking.getFullName() + ",\n\nYour consultation is complete. Please confirm within 24 hours. Report attached.",
//                report
//        );
//    }

    public void payBooking(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Can only pay PENDING bookings");
        }
        booking.setStatus(BookingStatus.PAID);
        bookingRepository.save(booking);

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Paid",
                "Your booking has been paid successfully. Please wait for the consultation."
        );
    }

    public void completeBooking(Integer bookingId, MultipartFile report) {
        Booking booking = getBookingOrThrow(bookingId);
        if (booking.getStatus() != BookingStatus.PAID) {
            throw new RuntimeException("Can only complete PAID bookings");
        }

        Slot slot = booking.getSlot();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (!currentDate.equals(slot.getAvailableDate()) ||
                currentTime.isBefore(slot.getStartTime()) ||
                currentTime.isAfter(slot.getEndTime())) {
            throw new RuntimeException("Booking can only be completed during the scheduled slot time");
        }

        String filePath = saveFile(report);
        booking.setStatus(BookingStatus.AWAITING_CONFIRMATION);
        booking.setMedicalReportPath(filePath);
        booking.setConfirmationDeadline(LocalDateTime.now().plusMinutes(30)); // Sửa thành 30 phút
        bookingRepository.save(booking);

        emailBookingService.sendEmailWithAttachment(
                booking.getEmail(),
                "Booking Awaiting Your Confirmation",
                "Dear " + booking.getFullName() + ",\n\nYour consultation is complete. Please confirm within 30 minutes. Report attached.",
                report
        );
    }

    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    @Transactional
    public void autoCompleteBookings() {
        List<Booking> awaitingBookings = bookingRepository.findByStatus(BookingStatus.AWAITING_CONFIRMATION);
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : awaitingBookings) {
            if (booking.getConfirmationDeadline() != null && now.isAfter(booking.getConfirmationDeadline())) {
                booking.setStatus(BookingStatus.COMPLETED);
                bookingRepository.save(booking);

                emailBookingService.sendEmail(
                        booking.getEmail(),
                        "Booking Auto-Completed",
                        "Dear " + booking.getFullName() + ",\n\nYour booking has been automatically completed due to no confirmation within 30 minutes."
                );
            }
        }
    }

    public void confirmBooking(Long userId, Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (!booking.getUser().getUserID().equals(userId)) {
            throw new RuntimeException("Unauthorized to confirm this booking");
        }
        if (booking.getStatus() != BookingStatus.AWAITING_CONFIRMATION) {
            throw new RuntimeException("Booking is not awaiting confirmation");
        }

        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Confirmed",
                "Dear " + booking.getFullName() + ",\n\nYour booking has been successfully confirmed."
        );
    }

    private String saveFile(MultipartFile file) {
        return "path/to/" + file.getOriginalFilename(); // Logic lưu file cần được triển khai thực tế
    }

    public void cancelBooking(Long userId, Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        if (!booking.getUser().getUserID().equals(userId)) {
            throw new RuntimeException("Unauthorized to cancel this booking");
        }
        if (booking.getStatus() != BookingStatus.PENDING) { // Chỉ cho phép hủy khi PENDING
            throw new RuntimeException("Cannot cancel a paid or completed booking");
        }
        Slot slot = booking.getSlot();
        if (slot != null) {
            booking.setStatus(BookingStatus.CANCELLED);
            slot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
            bookingRepository.save(booking);
            slotRepository.save(slot);
        }

        emailBookingService.sendEmail(
                booking.getEmail(),
                "Booking Cancelled",
                "You have successfully cancelled your booking."
        );
        emailBookingService.sendEmail(
                slot.getUser().getEmail(),
                "Booking Cancelled",
                "The booking for your slot on " + slot.getAvailableDate() + " has been cancelled."
        );
    }

    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    @Transactional
    public void autoCancelUnpaidBookings() {
        List<Booking> pendingBookings = bookingRepository.findByStatus(BookingStatus.PENDING);
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : pendingBookings) {
            LocalDateTime createdAt = booking.getCreatedAt(); // Giả sử Booking có field createdAt
            if (createdAt != null && now.isAfter(createdAt.plusMinutes(10))) { // Quá 10 phút
                booking.setStatus(BookingStatus.CANCELLED);
                Slot slot = booking.getSlot();
                if (slot != null) {
                    slot.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
                    slotRepository.save(slot);
                }
                bookingRepository.save(booking);

                // Gửi email thông báo
                emailBookingService.sendEmail(
                        booking.getEmail(),
                        "Booking Cancelled Due to Non-Payment",
                        "Dear " + booking.getFullName() + ",\n\nYour booking on " + slot.getAvailableDate() + " at " + slot.getStartTime() + " has been cancelled due to non-payment within 10 minutes."
                );
                emailBookingService.sendEmail(
                        slot.getUser().getEmail(),
                        "Booking Cancelled Due to Non-Payment",
                        "The booking for your slot on " + slot.getAvailableDate() + " at " + slot.getStartTime() + " has been cancelled due to non-payment."
                );
            }
        }
    }

    @Scheduled(fixedRate = 60000) // Chạy mỗi phút
    @Transactional
    public void autoCompleteOverdueBookings() {
        List<Booking> paidBookings = bookingRepository.findByStatus(BookingStatus.PAID);
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : paidBookings) {
            Slot slot = booking.getSlot();
            LocalDate slotDate = slot.getAvailableDate();
            LocalTime slotEndTime = slot.getEndTime();
            LocalDateTime slotEndDateTime = LocalDateTime.of(slotDate, slotEndTime);

            if (now.isAfter(slotEndDateTime)) { // Slot đã kết thúc
                booking.setStatus(BookingStatus.AWAITING_CONFIRMATION);
                booking.setConfirmationDeadline(LocalDateTime.now().plusMinutes(30)); // 30 phút để student xác nhận
                bookingRepository.save(booking);

                // Gửi email thông báo cho student
                emailBookingService.sendEmail(
                        booking.getEmail(),
                        "Booking Awaiting Your Confirmation",
                        "Dear " + booking.getFullName() + ",\n\nYour consultation on " + slot.getAvailableDate() + " at " + slot.getStartTime() + " has ended. Please confirm within 30 minutes."
                );
            }
        }
    }

    public List<BookingResponse> getUserBookings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Booking> bookings = bookingRepository.findByUser(user);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return bookings.stream().map(booking -> {
            Slot slot = booking.getSlot();
            User psychologist = slot.getUser();
            return new BookingResponse(
                    booking.getId(),
                    booking.getStatus().toString(),
                    "Booking retrieved successfully",
                    slot.getSlotId(),
                    slot.getAvailableDate().format(dateFormatter),
                    slot.getStartTime().toString(),
                    slot.getEndTime().toString(),
                    psychologist.getFullName(),
                    booking.getFee()
            );
        }).collect(Collectors.toList());
    }

    public BookingResponse getBookingDetails(Integer bookingId) {
        Booking booking = getBookingOrThrow(bookingId);
        Slot slot = booking.getSlot();
        User psychologist = slot.getUser();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        BookingResponse response = new BookingResponse(
                booking.getId(),
                booking.getStatus().toString(),
                "Booking details retrieved successfully",
                slot.getSlotId(),
                slot.getAvailableDate().format(dateFormatter),
                slot.getStartTime().toString(),
                slot.getEndTime().toString(),
                psychologist.getFullName(),
                booking.getFee()
        );

        response.setPsychologistDetails(new BookingResponse.PsychologistDetails(
                psychologist.getUserID(),
                psychologist.getFullName(),
                psychologist.getUserDetail().getMajor(),
                psychologist.getUserDetail().getDegree(),
                psychologist.getUserDetail().getWorkplace(),
                psychologist.getUserDetail().getFee()
        ));

        return response;
    }

    public List<BookingResponse> getPsychologistBookings(Long psychologistId) {
        User psychologist = userRepository.findById(psychologistId)
                .orElseThrow(() -> new RuntimeException("Psychologist not found"));

        List<Slot> slots = slotRepository.findByUser(psychologist);
        List<Booking> bookings = bookingRepository.findBySlotIn(slots);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return bookings.stream().map(booking -> {
            Slot slot = booking.getSlot();
            User client = booking.getUser();
            BookingResponse response = new BookingResponse(
                    booking.getId(),
                    booking.getStatus().toString(),
                    "Booking retrieved successfully",
                    slot.getSlotId(),
                    slot.getAvailableDate().format(dateFormatter),
                    slot.getStartTime().toString(),
                    slot.getEndTime().toString(),
                    slot.getUser().getFullName(),
                    booking.getFee()
            );
            response.setClientDetails(new BookingResponse.ClientDetails(
                    client.getUserID(),
                    client.getFullName(),
                    client.getEmail()
            ));
            return response;
        }).collect(Collectors.toList());
    }

    private Booking getBookingOrThrow(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<User> recommendPsychologists(Long userId) {
        // Lấy kết quả test gần nhất của user
        TestResult latestTestResult = testResultRepository.findTopByUser_UserIDOrderByCreateAtDesc(userId)
                .orElseThrow(() -> new RuntimeException("No test result found for user"));

        String testLevel = determineTestLevel(latestTestResult.getTotalScore(), latestTestResult.getTest().getId());

        // Gợi ý psychologist dựa trên mức độ
        List<User> psychologists = userRepository.findByRoleEnumAndIsDeletedFalse(RoleEnum.PSYCHOLOGIST);
        return psychologists.stream()
                .filter(p -> isSuitablePsychologist(p.getUserDetail().getMajor(), testLevel))
                .collect(Collectors.toList());
    }

    private String determineTestLevel(int score, Long testId) {
        List<TestScoring> scorings = testScoringRepository.findByTest_Id(testId);
        for (TestScoring scoring : scorings) {
            if (score >= scoring.getMinScore() && (scoring.getMaxScore() == null || score <= scoring.getMaxScore())) {
                return scoring.getLevel(); // Ví dụ: "Mild", "Moderate", "Severe"
            }
        }
        return "Unknown";
    }

    private boolean isSuitablePsychologist(String major, String testLevel) {
        // Logic đơn giản: Gán chuyên môn với mức độ
        if ("Minimal Anxiety".equals(testLevel) && major.contains("Clinical Psychology")) return true;
        if ("Mild Anxiety".equals(testLevel) && major.contains("Clinical Psychology")) return true;
        if ("Moderate Anxiety".equals(testLevel) && major.contains("Clinical Psychology")) return true;
        return false;
    }
}