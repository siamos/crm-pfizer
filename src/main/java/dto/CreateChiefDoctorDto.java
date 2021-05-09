package dto;

import lombok.Data;

@Data
public class CreateChiefDoctorDto implements IDto {
    private String role = "chiefDoctor";
}
