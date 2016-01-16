package org.phoenix.dao;

import org.phoenix.basic.impl.HibernateDaoImpl;
import org.phoenix.model.MsgBean;

public class MsgDao extends HibernateDaoImpl<MsgBean>{
	public MsgBean addMsgBean(MsgBean msgBean){
		return super.add(msgBean);
	}
	public void updateMsgBean(MsgBean msgBean){
		super.update(msgBean);
	}
}
