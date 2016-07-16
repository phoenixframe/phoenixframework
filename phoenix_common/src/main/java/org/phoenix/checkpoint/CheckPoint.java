package org.phoenix.checkpoint;

import org.junit.Assert;

/**
 * 检查点实现类
 * @author mengfeiyang
 *
 */
public class CheckPoint implements ICheckPoint{
	
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsEqual(java.lang.Object, java.lang.Object, java.lang.String)
	 */
	@Override
	public String checkIsEqual(Object expected,Object actual,String msg){
		String r = null;
		try{
			Assert.assertEquals(msg, expected, actual);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsEqual(java.lang.Object, java.lang.Object)
	 */
	@Override
	public String checkIsEqual(Object expected,Object actual){
		String r = null;
		try{
			Assert.assertEquals(expected, actual);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkArrayIsEqual(java.lang.Object[], java.lang.Object[], java.lang.String)
	 */
	@Override
	public String checkArrayIsEqual(Object[] expected,Object[] actual,String msg){
		String r = null;
		try{
			Assert.assertArrayEquals(msg,expected, actual);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkArrayIsEqual(java.lang.Object[], java.lang.Object[])
	 */
	@Override
	public String checkArrayIsEqual(Object[] expected,Object[] actual){
		String r = null;
		try{
			Assert.assertArrayEquals(expected, actual);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsFalse(boolean, java.lang.String)
	 */
	@Override
	public String checkIsFalse(boolean condition,String msg){
		String r = null;
		try{
			Assert.assertFalse(msg,condition);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsFalse(boolean, java.lang.String)
	 */
	@Override
	public String checkIsFalse(boolean condition){
		String r = null;
		try{
			Assert.assertFalse(condition);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkNotNull(java.lang.Object, java.lang.String)
	 */
	@Override
	public String checkNotNull(Object obj,String msg){
		String r = null;
		try{
			Assert.assertNotNull(msg,obj);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkNotNull(java.lang.Object, java.lang.String)
	 */
	@Override
	public String checkNotNull(Object obj){
		String r = null;
		try{
			Assert.assertNotNull(obj);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsNull(java.lang.Object, java.lang.String)
	 */
	@Override
	public String checkIsNull(Object obj,String msg){
		String r = null;
		try{
			Assert.assertNull(msg,obj);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsNull(java.lang.Object)
	 */
	@Override
	public String checkIsNull(Object obj){
		String r = null;
		try{
			Assert.assertNull(obj);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsTrue(boolean, java.lang.String)
	 */
	@Override
	public String checkIsTrue(boolean condition,String msg){
		String r = null;
		try{
			Assert.assertTrue(msg,condition);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsTrue(boolean)
	 */
	@Override
	public String checkIsTrue(boolean condition){
		String r = null;
		try{
			Assert.assertTrue(condition);
		} catch (AssertionError e){
			r = "info:"+e.getMessage();
		}
		return r;
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsMatcher(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String checkIsMatcher(String str1,String str2,String msg){
		if (str2.contains(str1))
			return null;
		else {
				return msg + ":false";
		}
	}
	/*
	 * (non-Javadoc)
	 * @see org.phoenix.action.ICheckPoint#checkIsMatcher(java.lang.String, java.lang.String)
	 */
	@Override
	public String checkIsMatcher(String str1,String str2){
		if (str2.contains(str1))
			return null;
		else {
				return "false";
		}
	}
}