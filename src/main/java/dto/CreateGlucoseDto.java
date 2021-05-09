package dto;

import lombok.Data;
import model.Patient;
import java.util.Date;

@Data
public class CreateGlucoseDto implements IDto {
    private double glucose;
    private Date date;
    private Patient patient;
}
