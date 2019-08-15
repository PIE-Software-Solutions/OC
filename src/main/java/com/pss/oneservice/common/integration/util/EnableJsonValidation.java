package com.pss.oneservice.common.integration.util;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.pss.oneservice.common.integration.annotations.IgnoreJsonValidation;
import static com.pss.oneservice.common.integration.config.OneServiceInit.oneServiceBootClass;

public class EnableJsonValidation implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		if(oneServiceBootClass == null) {
			return false;
		}

		if (!oneServiceBootClass.isAnnotationPresent(IgnoreJsonValidation.class)) {
			return true;
		}
		
		return false;
	}

}
