package dto;

import lombok.Data;
import model.Doctor;
import model.Patient;

import javax.persistence.ManyToOne;
import java.util.Date;

@Data
public class UpdateConsultationDto implements IDto {
    private long id;
    private Date date;
    private String medicationName;
    private double dosage;
    private String comment;
    private Patient patient;
    private Doctor doctor;
}
