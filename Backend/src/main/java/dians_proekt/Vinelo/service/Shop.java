package dians_project.vinelo.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Shop {
    private String id;
    private String address;
    private String email;
    private String name;
    private String phone;
    private String website;
    private String opening_hours;
    private String category;
    private double lat;
    private double lon;
    private double avgGrade;
    private List<Review> reviewList;

    public Shop() {
        this.reviewList = new ArrayList<>();
    }
}