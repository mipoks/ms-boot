package design.kfu.helper.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= EqualsOnValidator.class)
public @interface AssertOn {
    String message() default "You should accept agreement";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
