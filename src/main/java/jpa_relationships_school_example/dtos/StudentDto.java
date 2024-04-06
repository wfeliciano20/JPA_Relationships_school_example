package jpa_relationships_school_example.dtos;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class StudentDto {

    private String firstName;

    private String lastName;

    private String email;

    private int age;
}
