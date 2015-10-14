package com.skyline.sms.caster.cmd.parser;

import com.skyline.sms.caster.cmd.ExecuteResult;



public interface ResultParser {
	
		public Object parseResult(ExecuteResult exeResult);
		
		public Class<?> getReultType();
	
}
