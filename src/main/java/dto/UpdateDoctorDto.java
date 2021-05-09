package dto;

import lombok.Data;
import model.Consultation;
import model.Patient;
import java.util.Date;
import java.util.List;

@Data
public class UpdateDoctorDto implements IDto {
    private long id;
    private String role = "doctor";
    private Date recentConsultation;
    private List<Consultation> consultationList;
    private List<Patient> patientList;
}
