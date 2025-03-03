package be.mentalhealth.springboot_backend.api;

import be.mentalhealth.springboot_backend.entity.Choice;
import be.mentalhealth.springboot_backend.service.ChoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/choices")
public class ChoiceController {

    private final ChoiceService choiceService;

    public ChoiceController(ChoiceService choiceService) {
        this.choiceService = choiceService;
    }

    // Thêm lựa chọn vào câu hỏi đã tồn tại
    @PostMapping("/{questionId}")
    public ResponseEntity<Choice> addChoice(@PathVariable Long questionId, @RequestBody Choice choice) {
        return ResponseEntity.ok(choiceService.addChoice(questionId, choice));
    }

    // Xóa lựa chọn theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChoice(@PathVariable Long id) {
        try {
            choiceService.deleteChoice(id);
            return ResponseEntity.ok("Choice deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


