package fr.ldnr.FullStackBackend.entities;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class CityDTO {

    private Long id;
    private String name;
}
