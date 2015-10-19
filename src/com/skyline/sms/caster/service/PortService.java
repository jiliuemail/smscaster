package com.skyline.sms.caster.service;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.ExecuteResult;

public interface PortService {

	public String getPortStatus() throws Exception;

	ExecuteResult execute(Command cmd) throws Exception;
}
