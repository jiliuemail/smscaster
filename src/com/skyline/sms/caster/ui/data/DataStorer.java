package com.skyline.sms.caster.ui.data;

import java.util.List;

/**
 * 数据存储接口，在DataTable中会用到
 * 当修改或添加数据时会调用updateData方法
 * 当删除数据时会调用deleteData方法
 * 
 * @author linyn
 *
 * @since 2015年10月19日
 */
public interface DataStorer<T> {
	
	public boolean updateData(List<T> updatedData);
	
	public boolean deleteData(T deletedData);

}
