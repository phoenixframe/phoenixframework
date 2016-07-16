package org.phoenix.checkpoint;

/**
 * 检查点接口
 * @author mengfeiyang
 *
 */
public interface ICheckPoint {
	/**
	 * 校验obj1是否等于obj2
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	String checkIsEqual(Object obj1,Object obj2);
	/**
	 * 校验obj1是否等于obj2
	 * @param obj1
	 * @param obj2
	 * @param msg  如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkIsEqual(Object obj1,Object obj2,String msg);
	/**
	 * 校验obj1是否等于obj2
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	String checkArrayIsEqual(Object[] obj1,Object[] obj2);
	/**
	 * 校验obj1是否等于obj2
	 * @param obj1
	 * @param obj2
	 * @param msg  如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkArrayIsEqual(Object[] obj1,Object[] obj2,String msg);
	/**
	 * 校验是否为false
	 * @param condition
	 * @return
	 */
	String checkIsFalse(boolean condition);
	/**
	 * 校验是否为false
	 * @param condition
	 * @param msg  如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkIsFalse(boolean condition,String msg);
	/**
	 * 校验obj是否不为null
	 * @param obj
	 * @param msg 如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkNotNull(Object obj,String msg);
	/**
	 * 校验obj是否不为null
	 * @param obj
	 * @return
	 */
	String checkNotNull(Object obj);
	/**
	 * 校验obj是否为null
	 * @param obj
	 * @param msg  如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkIsNull(Object obj,String msg);
	/**
	 * 校验obj是否为null
	 * @param obj
	 * @return
	 */
	String checkIsNull(Object obj);
	/**
	 * 校验condition是否为true
	 * @param condition
	 * @param msg 如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkIsTrue(boolean condition,String msg);
	/**
	 * 校验condition是否为true
	 * @param condition
	 * @return
	 */
	String checkIsTrue(boolean condition);
	/**
	 * 校验str2是否包含str1
	 * @param str1
	 * @param str2
	 * @param msg  如果校验失败，此消息会附加到测试结果的消息上
	 * @return
	 */
	String checkIsMatcher(String str1,String str2,String msg);
	/**
	 * 校验str2是否包含str1
	 * @param str1
	 * @param str2
	 * @return
	 */
	String checkIsMatcher(String str1,String str2);

}
