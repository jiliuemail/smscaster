package com.skyline.sms.caster.dao;

import com.skyline.sms.caster.util.FormatUtil;
import com.skyline.sms.caster.util.StringUtil;

public class QueryCondition {
	
	private String paramName;
	
	private Object paramValue;
	
	private ConditionType conditionType;
	
	
	public static QueryCondition equals(String paramName, Object paramValue){
		return new QueryCondition(paramName, paramValue);
	}
	
	public static QueryCondition like(String paramName, Object paramValue){
		return new QueryCondition(paramName, paramValue, ConditionType.LIKE);
	}
	
	public static QueryCondition rightLike(String paramName, Object paramValue){
		return new QueryCondition(paramName, paramValue, ConditionType.RIGHT_LIKE);
	}
	

	public QueryCondition(String paramName, Object paramValue) {
		super();
		this.paramName = paramName;
		this.paramValue = paramValue;
		conditionType = ConditionType.EQUALS;
	}

	public QueryCondition(String paramName, Object paramValue, ConditionType conditionType) {
		super();
		this.paramName = paramName;
		this.paramValue = paramValue;
		this.conditionType = conditionType;
	}

	protected String getParamName() {
		return paramName;
	}

	protected void setParamName(String paramName) {
		this.paramName = paramName;
	}

	protected Object getParamValue() {
		return paramValue;
	}

	protected void setParamValue(Object paramValue) {
		this.paramValue = paramValue;
	}

	protected ConditionType getConditionType() {
		return conditionType;
	}

	protected void setConditionType(ConditionType conditionType) {
		this.conditionType = conditionType;
	}
	
	public String formatedParamValue(){
		return FormatUtil.formatToString(paramValue);
	}

	public boolean isValidCondition(){
		return (StringUtil.hasText(paramName) && paramValue != null);
	}
}
