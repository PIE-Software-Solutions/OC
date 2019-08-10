package com.pss.oneservice.common.integration.config;

import java.util.Arrays;
import java.util.List;
 
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static com.pss.oneservice.common.integration.util.CommonConstants.PC_REQ;
import static com.pss.oneservice.common.integration.util.CommonConstants.SEC_REQ;
import static com.pss.oneservice.common.integration.util.CommonConstants.JSON_VAL_REQ;
 
/***
 * Custom validator class to validate custom annotation {@code SupportedValues}
 *
 * @author THREADMINIONS
 *
 */
public class CustomValidator implements ConstraintValidator<SetValidation, String>{
 
    @Override
    public void initialize(SetValidation setValidation)
    {
    	PC_REQ = setValidation.PC_REQ();
    	SEC_REQ = setValidation.SEC_REQ();
    	JSON_VAL_REQ = setValidation.JSON_VAL_REQ();
    }
 
    @Override
    public boolean isValid(String value, ConstraintValidatorContext arg1)
    { 
        return true;
    }
 
}
