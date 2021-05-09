package dto;

import lombok.Data;
import model.Doctor;
import model.Patient;
import java.util.Date;

@Data
public class CreateConsultationDto implements IDto {
    private Date date;
    private String medicationName;
    private double dosage;
    private String comment;
    private Patient patient;
    private Doctor doctor;
}
