package Service;

import representation.IDto;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class ValidationDto {

    public Boolean dtoValidation(IDto validationDto)
    {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.usingContext().getValidator();

        Set<ConstraintViolation<IDto>> constrains = validator.validate(validationDto);
        if (constrains != null) {
            for (ConstraintViolation<IDto> constrain : constrains) {
                System.out.println(
                        "[" + constrain.getPropertyPath() + "][" + constrain.getMessage() + "]"
                );
            }
            return false;
        }

        return true;
    }
}
