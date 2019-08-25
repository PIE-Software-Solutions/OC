package in.iampsk.oneservice.common.integration.util;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import in.iampsk.oneservice.common.integration.annotations.EnableJdbcSecurity;
import in.iampsk.oneservice.common.integration.annotations.EnableOAuth2JdbcSecurity;
import in.iampsk.oneservice.common.integration.annotations.EnableOAuth2JdbcServer;
import in.iampsk.oneservice.common.integration.annotations.EnablePropSecurity;
import in.iampsk.oneservice.common.integration.annotations.IgnoreSecurity;

import static in.iampsk.oneservice.common.integration.config.OneServiceInit.oneServiceBootClass;

public class NoSecurityCondition implements Condition {
	
	private static final String METHOD = "NoSecurityCondition";
	
	private static final AppLogger LOGGER = new AppLogger(Condition.class.getName());

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		String message = "";
		if(oneServiceBootClass == null) {
			return false;
		}

		if (oneServiceBootClass.isAnnotationPresent(IgnoreSecurity.class)) {
			try {
				if (oneServiceBootClass.isAnnotationPresent(EnableJdbcSecurity.class) || oneServiceBootClass.isAnnotationPresent(EnablePropSecurity.class) || oneServiceBootClass.isAnnotationPresent(EnableOAuth2JdbcSecurity.class) || oneServiceBootClass.isAnnotationPresent(EnableOAuth2JdbcServer.class)) {
					message = "IgnoreSecurity can't combined with Anyother security methods. Please removed other security Methods.";
					LOGGER.error(METHOD, message);
					throw new IllegalArgumentException(message);
				}else {
					return true;
				}
			}catch(IllegalArgumentException e) 
	        {
	            try {
					throw e;
				} catch (IllegalArgumentException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(-100);
				} // rethrowing the exception 
	        }
		}
		
		return false;
	}

}
