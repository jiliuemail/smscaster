package com.skyline.sms.caster.ui.data;

import java.util.List;

import javax.swing.JFrame;

import com.skyline.sms.caster.util.DialogUtil;
import com.skyline.sms.caster.util.LogUtil;

/**
 * 提供默认实现的数据存储接口，更新或删除数据时会有提示
 * 
 * @author linyn
 *
 * @since 2015年10月19日
 */
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
	
	@Override
	public boolean deleteData(T deletedData) {
		try {
			submitDeletedData(deletedData);
			DialogUtil.showDeleteOK();
			return true;
		} catch (Exception e) {
			LogUtil.error(e);
			DialogUtil.showDeleteError();
			return false;
		}
	}
	
	protected abstract void submitDeletedData(T deletedData) throws Exception;
}
