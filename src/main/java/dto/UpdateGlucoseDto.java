package dto;

import lombok.Data;
import model.Patient;
import java.util.Date;

@Data
public class UpdateGlucoseDto implements IDto {
    private long id;
    private double glucose;
    private Date date;
    private Patient patient;
}
