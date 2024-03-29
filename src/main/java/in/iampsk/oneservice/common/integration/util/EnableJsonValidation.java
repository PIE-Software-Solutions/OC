package in.iampsk.oneservice.common.integration.util;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import in.iampsk.oneservice.common.integration.annotations.IgnoreRequestValidation;
import static in.iampsk.oneservice.common.integration.config.OneServiceInit.oneServiceBootClass;

public class EnableJsonValidation implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		if(oneServiceBootClass == null) {
			return false;
		}

		if (!oneServiceBootClass.isAnnotationPresent(IgnoreRequestValidation.class)) {
			return true;
		}
		
		return false;
	}

}
