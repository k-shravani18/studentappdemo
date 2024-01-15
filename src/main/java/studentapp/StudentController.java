package studentapp;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static List<Student> students = new ArrayList<>();

    // Initialize with some hardcoded values
    static {
        students.add(new Student(1L, "John Doe", 20));
        students.add(new Student(2L, "Jane Doe", 22));
        students.add(new Student(3L, "Alice Smith", 21));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        student.setId(System.currentTimeMillis()); // Simple ID generation
        students.add(student);
        return student;
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst()
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setAge(updatedStudent.getAge());
                    return student;
                })
                .orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        students.removeIf(student -> student.getId().equals(id));
    }
}
