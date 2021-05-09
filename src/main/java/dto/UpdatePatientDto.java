package dto;

import lombok.Data;
import model.Carb;
import model.Consultation;
import model.Doctor;
import model.Glucose;
import java.util.Date;
import java.util.List;

@Data
public class UpdatePatientDto implements IDto {
    private long id;
    private int age;
    private String sex;
    private Date dateRegistered;
    private String role = "patient";
    private boolean consultationChanged = false;
    private Date recentConsultation;
    private Date recentCarb;
    private Date recentGlucose;
    private Doctor doctor;
    private List<Carb> carbList;
    private List<Glucose> glucoseList;
    private List<Consultation> consultationList;
}
