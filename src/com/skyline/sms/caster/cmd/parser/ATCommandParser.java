package com.skyline.sms.caster.cmd.parser;

import com.skyline.sms.caster.cmd.ExecuteResult;

public class ATCommandParser implements ResultParser {

	@Override
	public String parseResult(ExecuteResult exeResult) {
		return exeResult.getResult();
	}

	@Override
	public Class<?> getReultType() {
		return String.class;
	}

}
