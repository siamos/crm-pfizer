package dto;

import lombok.Data;

@Data
public class UpdateChiefDoctorDto implements IDto {
    private long id;
    private String role = "chiefDoctor";
}
