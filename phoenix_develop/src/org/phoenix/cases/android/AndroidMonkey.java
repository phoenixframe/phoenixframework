package org.phoenix.cases.android;

import java.io.IOException;

import org.phoenix.utils.CommandExecutor;

/**
 * Android monkey测试
 * @author mengfeiyang
 *
 */
public class AndroidMonkey {
	
	public static void main(String[] args) throws IOException {
		String command = "adb shell monkey -p combaozhang.com -vvv 1000";
		System.out.println(CommandExecutor.executeWindowsCmd(command));
	}
}
