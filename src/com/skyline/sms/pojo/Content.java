package com.skyline.sms.pojo;

import java.nio.ByteBuffer;

public class Content {
	

	private String content;

	
	public Content(String content) {
		super();
		this.content = content;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

}
