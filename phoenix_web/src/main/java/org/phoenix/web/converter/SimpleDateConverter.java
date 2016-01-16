package org.phoenix.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 配置日期转换器。在参数提交过程中会自动做转换
 * @author mengfeiyang
 *
 */
public class SimpleDateConverter implements Converter<String,Date>{

	@Override
	public Date convert(String arg0) {
		SimpleDateFormat simDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return simDate.parse(arg0);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
