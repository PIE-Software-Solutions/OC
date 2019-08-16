package in.iampsk.oneservice.common.integration.util;

import in.iampsk.oneservice.common.integration.model.UnKnownResponseModel;

import net.sf.cglib.beans.BeanMap;

public class ResponsePojoGenerator {
	
	public static UnKnownResponseModel getResponse(Object obj) {
		UnKnownResponseModel knownResponseModel = new UnKnownResponseModel();
		BeanMap beanMap = PojoGenerator.generateBean(obj);
		if(null != beanMap)
			knownResponseModel.setBeanMap(beanMap);		
		return knownResponseModel;
	}

}
