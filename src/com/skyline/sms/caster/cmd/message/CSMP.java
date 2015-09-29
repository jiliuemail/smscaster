package com.skyline.sms.caster.cmd.message;


import com.skyline.sms.caster.cmd.atcmd.ATCommand;
import com.skyline.sms.caster.util.StringUtil;

public class CSMP extends ATCommand {
	
	private Integer fo;
	private Integer vp;
	private Integer pid;
	private Integer dcs;
	
	public CSMP(Integer dcs) {
		super();
		this.dcs = dcs;
	}

	public CSMP(Integer fo, Integer vp, Integer pid, Integer dcs) {
		super();
		this.fo = fo;
		this.vp = vp;
		this.pid = pid;
		this.dcs = dcs;
	}

	@Override
	protected String getCommandParam() {
		return StringUtil.concattValue(fo, vp, pid, dcs);
	}
	
	


}
