package org.phoenix.web.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * 对于js注入相关的字符做转换。在参数提交过程中会自动做转换
 * @author mengfeiyang
 *
 */
public class JSConverter implements Converter<String,String>{

	@Override
	public String convert(String arg0) {
		if(arg0.contains("<script")){
			return arg0.replace("<script", "〈script");
		}
		return arg0;
	}
}
