package fr.ldnr.FullStackBackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String phoneNumber;
    private String adress;
    private int rating;
    private int roomAvailability;
    private int review;
    private double lowestRoomPrice;
    private String img;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
