package com.skyline.sms.pojo;

public class Message {
	
	private Contacter contacter;
	private Content content;
	
	public Message(Contacter contacter, Content content) {
		super();
		this.contacter = contacter;
		this.content = content;
	}

	public Contacter getContacter() {
		return contacter;
	}

	public void setContacter(Contacter contacter) {
		this.contacter = contacter;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
	
	public String getSentMessage(){
		return contacter.getPhoneNumber()+"\n"+content.getByteBuffer()+"\\x1A";
	} 


	
	
}
