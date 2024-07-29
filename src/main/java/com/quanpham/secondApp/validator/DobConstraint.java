package com.quanpham.secondApp.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD}) // de no trong field
@Retention(RetentionPolicy.RUNTIME) // duoc xu ly khi nao -> runtime
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {
    String message() default "Invalid Date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};    // 3 truong nay luon can

    int min();
}
