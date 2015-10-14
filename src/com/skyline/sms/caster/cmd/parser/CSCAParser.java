package com.skyline.sms.caster.cmd.parser;

import com.skyline.sms.caster.cmd.ExecuteResult;

public class CSCAParser extends ATCommandParser {
	@Override
	public String parseResult(ExecuteResult exeResult) {
		// TODO Auto-generated method stub
		String result=exeResult.getResult();
		if(exeResult.isOK()){
			result= result.substring(result.indexOf("CSCA:")+7,result.indexOf("\","));
		}
		return result;
	}

}
