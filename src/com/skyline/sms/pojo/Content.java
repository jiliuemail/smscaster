package com.skyline.sms.pojo;

import java.nio.ByteBuffer;

public class Content {
	
	private ByteBuffer byteBuffer;

	public Content(ByteBuffer byteBuffer) {
		super();
		this.byteBuffer = byteBuffer;
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}
	
}
