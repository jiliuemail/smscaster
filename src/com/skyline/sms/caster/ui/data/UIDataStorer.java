package com.skyline.sms.caster.ui.data;

import java.util.List;

import javax.swing.JFrame;

import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;

public abstract class UIDataStorer<T> implements DataStorer<T> {
	
	private JFrame owner;
	
	public UIDataStorer(JFrame owner){
		this.owner = owner;
	}

	@Override
	public boolean updateData(List<T> updatedData) {
		try {
			if (updatedData.size() > 0) {
				submitUpdatedData(updatedData);
				DialogUtil.showSaveOK(owner);
				return true;
			}else{
				DialogUtil.showToast("sms.caster.message.toast.nodata.update");
				return false;
			}
		} catch (Exception e1) {
			LogUtil.error(e1);
			DialogUtil.showSaveError(owner);
			return false;
		}
	}
	
	protected abstract void submitUpdatedData(List<T> updatedData) throws Exception;
}
