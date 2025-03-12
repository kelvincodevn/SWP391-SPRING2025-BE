package be.mentalhealth.springboot_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
 // Lombok tự động tạo constructor có tham số cho tất cả các trường
public class BookingResponse {
    private int bookingId;
    private String status;
    private String message;

    public BookingResponse(){

    }

    public BookingResponse(int bookingId, String status, String message) {
        this.bookingId = bookingId;
        this.status = status;
        this.message = message;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
