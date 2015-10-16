package com.skyline.sms.caster.ui.data;

import java.util.List;

public interface DataStorer<T> {
	
	public boolean updateData(List<T> updatedData);

}
