package com.skyline.sms.caster.dao;

public enum ConditionType {
	
	EQUALS, // =
	
	NOT_EQUALS, // !=
	
	LIKE, // LIKE %VALUE%
	
	LEFT_LIKE, // LIKE %VALUE
	
	RIGHT_LIKE, // LIKE VALUE%
	
	LT, // >
	
	RT, // <
	
	LGT, // >=
	
	RGT, // <=

	NULL, // IS NULL
	
	NOT_NULL, // IS NOT NULL
	
	IN, // IN(...)
	
	NOT_IN // NOT IN(...)
}
