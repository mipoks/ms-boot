package design.kfu.helper.validation;


import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
public class AssertEqualsValidator implements ConstraintValidator<AssertEquals, Object> {
    private String fieldOne;
    private String fieldTwo;


    @Override
    public void initialize(AssertEquals annotation) {
        fieldOne = annotation.fieldOne();
        fieldTwo = annotation.fieldTwo();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {
        try {
            log.info(fieldOne);
            log.info(fieldTwo);
            Class temp = value.getClass();
            Field field1 = temp.getDeclaredField(fieldOne);
            Field field2 = temp.getDeclaredField(fieldTwo);
            field1.setAccessible(true);
            field2.setAccessible(true);
            fieldOne = (String) field1.get(value);
            fieldTwo = (String) field2.get(value);
            log.info(fieldOne);
            log.info(fieldTwo);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (fieldOne != null)
            return fieldOne.equals(fieldTwo);
        else {
            if (fieldTwo == null)
                return true;
        }
        return false;
    }
}
