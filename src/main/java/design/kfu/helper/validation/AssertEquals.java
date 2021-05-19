package design.kfu.helper.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AssertEqualsValidator.class)
public @interface AssertEquals {
    String fieldOne();
    String fieldTwo();

    String message() default "Passwords are different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
