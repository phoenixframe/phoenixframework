package org.phoenix.node.dao;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.node.model.SlaveModel;
/**
 * 执行机表操作
 * @author mengfeiyang
 *
 */
public class SlaveDao extends HibernateDaoImpl<SlaveModel>{
	public void updateModel(SlaveModel s){
		super.update(s);
	}
}
