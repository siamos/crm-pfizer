package dto;

import lombok.Data;

@Data
public class CreateUserDto implements IDto {
    private String username;
    private String password;
    private String name;
    private String email;
    private String address;
}
