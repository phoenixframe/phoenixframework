package org.phoenix.api.utils;

import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class WSDLTest {
	public String invokeRemoteFuc() {
		String endpoint = "http://webservice.webxml.com.cn/WebServices/ChinaZipSearchWebService.asmx";
		String result = "no result!";
		Service service = new Service();
		Call call;
		String[] object = new String[]{"安徽省","阜阳市","阜南县"};// Object是用来存储方法的参数
		try {
			call = (Call) service.createCall();
			//call.setUseSOAPAction(true);
			//call.setOperationStyle(Style.DOCUMENT);
			call.setSOAPActionURI("http://WebXml.com.cn/getZipCodeByAddress");
			call.setTargetEndpointAddress(endpoint);// 远程调用路径
			call.setOperationName("getZipCodeByAddress");// 调用的方法名

			// 设置参数名:
			call.addParameter("theProvinceName", // 参数名
					XMLType.XSD_STRING,// 参数类型:String
					ParameterMode.IN);// 参数模式：'IN' or 'OUT'
			call.addParameter("theCityName", // 参数名
					XMLType.XSD_STRING,// 参数类型:String
					ParameterMode.IN);// 参数模式：'IN' or 'OUT'
			call.addParameter("theAddress", // 参数名
					XMLType.XSD_STRING,// 参数类型:String
					ParameterMode.IN);// 参数模式：'IN' or 'OUT'

			// 设置返回值类型：
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String

			result = (String) call.invoke(object);// 远程调用
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		WSDLTest t = new WSDLTest();
		String result = t.invokeRemoteFuc();
		System.out.println(result);
	}
}
