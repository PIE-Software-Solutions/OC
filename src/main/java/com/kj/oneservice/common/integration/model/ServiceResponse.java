package com.kj.oneservice.common.integration.model;

import static com.kj.oneservice.common.integration.util.CommonConstants.REQUEST_PATTERN;
import static com.kj.oneservice.common.integration.util.CommonConstants.SUCCESS_RESPONSE;
import static com.kj.oneservice.common.integration.util.SwaggerConstants.EXP_REQUEST_ID;
import static com.kj.oneservice.common.integration.util.SwaggerConstants.EXP_RESPONSE_CODE;
import static com.kj.oneservice.common.integration.util.SwaggerConstants.EXP_PROCESS_TIME_ID;

import java.util.List;

import org.apache.log4j.MDC;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * Abstract class to hold service response values.
 * 
 * <!-- This Class DOES NOT require any modification.-->
 * 
 * @author KARUNAR
 */
@SuppressWarnings("deprecation")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
public abstract class ServiceResponse {

	@JsonProperty(value = "REQUEST_ID", required = true)
	@ApiModelProperty(position = 1, example = EXP_REQUEST_ID)
	private Object requestId = MDC.get(REQUEST_PATTERN);

	@JsonProperty(value = "RESPONSE_CODE")
	@ApiModelProperty(position = 4, example = EXP_RESPONSE_CODE)
	private Integer responseCode;

	@JsonProperty(value = "RESPONSE_MSG", required = true)
	@ApiModelProperty(position = 5, example = SUCCESS_RESPONSE)
	private String responseMsg;
	
	@JsonProperty(value = "PROCESS_TIME")
	@ApiModelProperty(position = 6, example = EXP_PROCESS_TIME_ID)
	private Object requestTime;

	@JsonProperty(value = "ERROR")
	@ApiModelProperty(hidden = true)
	private List<ErrorDetails> error;

	public Object getRequestId() {
		return requestId;
	}

	public void setRequestId(Object requestId) {
		this.requestId = requestId;
	}

	public void setRequestTime(Object requestTime) {
		this.requestTime = requestTime;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public List<ErrorDetails> getError() {
		return error;
	}

	public void setError(List<ErrorDetails> error) {
		this.error = error;
	}
	
	
}
