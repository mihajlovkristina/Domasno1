package dians_project.vinelo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    private String username;
    private String comment;
    private int grade;
    private String gender;
    private String timestamp;
}