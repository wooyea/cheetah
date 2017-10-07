/*
 [The "BSD license"]
 Copyright (c) 2011-2014 Joel Li (李家智)
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:
 1. Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
     derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.beetl.core.om;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
/**
 * java对象一些操作util类，并缓存一些中间结果以提高性能
 * @author 
 *
 */
public class ObjectUtil
{

	/**深度复制一个对象，该对象及其属性都必须实现java.io.Serializable方法。此方法用于
	 * TypeEngine引擎复制一个Program，并做优化用
	 * @param o
	 * @return
	 */
	public static Object copy(Object o)
	{
		if (o instanceof java.io.Serializable)
		{
			try
			{
				ByteArrayOutputStream bs = new ByteArrayOutputStream(128);
				ObjectOutputStream dos = new ObjectOutputStream(bs);
				dos.writeObject(o);
				ByteArrayInputStream is = new ByteArrayInputStream(bs.toByteArray());
				ObjectInputStream ios = new ObjectInputStream(is);
				Object copy = ios.readObject();
				return copy;

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}

	/** 已知属性名，得出get方法，如属性名是name,get方法是getName
	 * 遵循javabean规范
	 * @param attrName
	 * @return
	 * @deprecated 并不遵循java规范
	 */
	public static String getGetMethod(String attrName)
	{
		StringBuilder mbuffer = new StringBuilder("get");
		mbuffer.append(attrName.substring(0, 1).toUpperCase()).append(attrName.substring(1));
		return mbuffer.toString();
	}

	/** 已知属性名，得出set方法，如属性名是name,get方法是setName
	 * 遵循javabean规范
	 * @param attrName
	 * @return
	 * @deprecated 并不遵循java规范
	 */
	public static String getSetMethod(String attrName)
	{
		StringBuilder mbuffer = new StringBuilder("set");
		mbuffer.append(attrName.substring(0, 1).toUpperCase()).append(attrName.substring(1));
		return mbuffer.toString();
	}

	/** 已知属性名，得出is方法，如属性名是boy,is方法是isBoy
	 * 遵循javabean规范
	 * @param attrName
	 * @return
	 * @deprecated 并不遵循java规范
	 */
	public static String getIsMethod(String attrName)
	{
		StringBuilder mbuffer = new StringBuilder("is");
		mbuffer.append(attrName.substring(0, 1).toUpperCase()).append(attrName.substring(1));
		return mbuffer.toString();
	}

	/** 调用类的静态方法，只知道方法名和参数，beetl将自动匹配到能调用的方法
	 * @param target
	 * @param methodName
	 * @param paras
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object invokeStatic(Class target, String methodName, Object[] paras) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		return invoke(target, null, methodName, paras);

	}

	public static Object invokeObject(Object o, String methodName, Object[] paras) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		Class target = o.getClass();
		return invoke(target, o, methodName, paras);

	}

	private static Object invoke(Class target, Object o, String methodName, Object[] paras) {
		return null;
	}

	/** 针对Class.forName的一个简单封装，根据类名获得类
	 * @param clsName
	 * @return 如果未加载成功，则抛出Runtime异常
	 */
	public static Class getClassByName(String clsName)
	{
		try
		{
			return Class.forName(clsName);
		}

		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**针对Class.forName(clsname).newInstance()的一个简单封装
	 * @param clsName
	 * @return 如果未能创建实例，则抛出runtime异常
	 */
	public static Object instance(String clsName)
	{
		try
		{
			return Class.forName(clsName).newInstance();
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	/** 实例化一个类，如果不成功，返回null
	 * @param clsName
	 * @return
	 */
	public static Object tryInstance(String clsName)
	{
		try
		{
			return instance(clsName);
		}
		catch (Exception ex)
		{
			return null;
		}
	}

}
