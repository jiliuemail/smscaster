package com.skyline.sms.caster.cmd.parser;

import java.util.HashMap;
import java.util.Map;

import com.skyline.sms.caster.cmd.Command;
import com.skyline.sms.caster.cmd.ExecuteResult;
import com.skyline.sms.caster.cmd.atcmd.CommandDelegate;
import com.skyline.sms.caster.cmd.message.CSCA;
import com.skyline.sms.caster.cmd.message.CSQ;

public class ParserRegister {
	private static Map<Class<? extends Command>, ResultParser> parsers;
	
	static{
		initMap();
		
		regist(CSQ.class, new CSQParser());
		regist(CSCA.class, new CSCAParser());
	}
	
	private static  void initMap(){
		 parsers=new HashMap<Class<? extends Command>, ResultParser>();
	}
	
	public static void regist(Class<? extends Command> cmdClass, ResultParser parser){
		parsers.put(cmdClass, parser);
	}

	public static <T>  T parserCommandResult(Command cmd, ExecuteResult exeResult, Class<T> resultType){
		if(cmd instanceof CommandDelegate){
			cmd = ((CommandDelegate)cmd).getCommand();
		}
		ResultParser parser = parsers.get(cmd.getClass());
		if(parser.getReultType().equals(resultType)){
			return resultType.cast(parser.parseResult(exeResult));
		}
		throw new ClassCastException("Pase command (" + cmd.getClass().getName() + ") result error.");
	}
}
