package model;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
public class Glucose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double glucose;
    private Date date;
    @ManyToOne
    private Patient patient;
}
