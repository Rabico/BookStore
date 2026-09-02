package book.store.dto.user;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            Field firstField = value.getClass().getDeclaredField(firstFieldName);
            firstField.setAccessible(true);
            Object firstValue = firstField.get(value);
            Field secondField = value.getClass().getDeclaredField(secondFieldName);
            secondField.setAccessible(true);
            Object secondValue = null;
            secondValue = secondField.get(value);

            return (firstValue == null && secondValue == null)
                    || (firstValue != null && firstValue.equals(secondValue));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
