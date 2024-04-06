package jpa_relationships_school_example.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    private String name;

    private String department;

    @JsonManagedReference
    @ManyToMany(mappedBy = "courses", cascade = jakarta.persistence.CascadeType.ALL, fetch = jakarta.persistence.FetchType.LAZY)
    private List<Enrollment> enrollments;


}
