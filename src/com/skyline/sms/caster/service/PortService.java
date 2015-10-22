package com.skyline.sms.caster.service;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.pojo.TMessage;

public interface PortService {

	public String getPortStatus() throws Exception;

	ExecuteResult execute(Command cmd) throws Exception;

	ExecuteResult sendSms(TMessage sms) throws Exception;

	void init() throws Exception;
}
