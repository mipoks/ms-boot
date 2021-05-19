package design.kfu.helper.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsOnValidator implements ConstraintValidator<AssertOn, String> {
    @Override
    public boolean isValid(String str, ConstraintValidatorContext constraintValidatorContext) {
        if (str != null)
            return str.toLowerCase().equals("on");
        else
            return false;
    }
}
