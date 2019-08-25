package in.iampsk.oneservice.common.integration.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


@Target({METHOD,TYPE})
@Retention(RUNTIME)
@Documented
@RequestMapping
@ResponseStatus(HttpStatus.CREATED)
public @interface SetContextMapping {
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] path() default {};
}