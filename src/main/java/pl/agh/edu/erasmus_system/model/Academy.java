package pl.agh.edu.erasmus_system.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "academies")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;
}
