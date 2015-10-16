package com.skyline.sms.caster.cmd.parser;

import com.skyline.sms.caster.cmd.ExecuteResult;



public class CSQParser extends ATCommandParser  implements ResultParser {

	@Override
	public  String parseResult(ExecuteResult exeResult) {
		// TODO Auto-generated method stub
		String result=exeResult.getResult();
		if(exeResult.isOK()){
			result= result.substring(result.indexOf("+CSQ:")+6,result.indexOf(","));
		}
		return result;
	}
}
