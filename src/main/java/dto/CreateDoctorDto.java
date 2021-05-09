package dto;

import lombok.Data;
import model.Consultation;
import model.Patient;
import java.util.Date;
import java.util.List;

@Data
public class CreateDoctorDto implements IDto {

    private String role = "doctor";
    private Date recentConsultation;
    private List<Consultation> consultationList;
    private List<Patient> patientList;
}
