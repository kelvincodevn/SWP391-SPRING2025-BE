package be.mentalhealth.springboot_backend.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
<<<<<<< HEAD

=======
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
@Builder
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "slot_id")
=======
>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
    private Integer slotId;

    private LocalDate availableDate;
    private LocalTime startTime;
    private LocalTime endTime;
<<<<<<< HEAD

    public Slot() {
    }

    public Slot(Integer slotId, LocalDate availableDate, LocalTime startTime, LocalTime endTime, boolean available) {
        this.slotId = slotId;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
    }

    public static SlotBuilder builder() {
        return new SlotBuilder();
    }

    public static class SlotBuilder {
        private Integer slotId;
        private LocalDate availableDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private boolean available = true;

        public SlotBuilder slotId(Integer slotId) {
            this.slotId = slotId;
            return this;
        }

        public SlotBuilder availableDate(LocalDate availableDate) {
            this.availableDate = availableDate;
            return this;
        }

        public SlotBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public SlotBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public SlotBuilder available(boolean available) {
            this.available = available;
            return this;
        }

        public Slot build() {
            return new Slot(slotId, availableDate, startTime, endTime, available);
        }
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    @Getter
    @Setter
    private boolean available = true; // Thêm cột này nếu chưa có

}
=======
}

>>>>>>> b4598932fd958f7395090188bcb5baf28566ac0c
