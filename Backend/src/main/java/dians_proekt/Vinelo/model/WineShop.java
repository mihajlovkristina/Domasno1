package dians_project.vinelo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DummyShop {
    private String id;
    private String address;
    private String name;
    private String category;
    private double avgGrade;
    private double lat;
    private double lon;

    public DummyShop(String address, String name, String category, double avgGrade, double lat, double lon) {
        this.address = address;
        this.name = name;
        this.category = category;
        this.avgGrade = avgGrade;
        this.lat = lat;
        this.lon = lon;
    }
}