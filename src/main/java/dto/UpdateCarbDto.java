package dto;

import lombok.Data;
import model.Patient;

import java.util.Date;

@Data
public class UpdateCarbDto implements IDto {
    private long id;
    private double carb;
    private Date date;
    private Date simpleDate;
    private Patient patient;
}
