package hello.hellospring.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Description("test")
@JsonView
public @interface CustomAnotationTest {
    String value() default "";
}
