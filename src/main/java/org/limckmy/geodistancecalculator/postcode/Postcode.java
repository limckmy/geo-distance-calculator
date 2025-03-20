package org.limckmy.geodistancecalculator.postcode;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "postcode")
public class Postcode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postcode_seq")
    //@SequenceGenerator(name = "seq_user_id", sequenceName = "seq_user_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String postcode;

    private double latitude;
    private double longitude;
}
