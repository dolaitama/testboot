package com.sugon.core.dto.gson;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @类名: GsonUtils.java
 * @描述: GSON使用的工具类。 一般情况下，请使用createCommonBuilder方法来创建GsonBuilder，然后再获得Gson对象，
 *      而尽量不要直接构建Gson对象。 如果需要增加自定义的格式控制，可以在利用createCommonBuilder创建GsonBuilder之后，
 *      再给该GsonBuilder增加自定义能力。
 */
public class GsonUtils {
	
	/**
	 * 使用常用配置创建一个GsonBuilder实例
	 */
	public static GsonBuilder createCommonBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new DateSerializer())
				.registerTypeHierarchyAdapter(Calendar.class, new CalendarSerializer())
				.registerTypeHierarchyAdapter(Timestamp.class, new TimestampSerializer()).serializeNulls()
				.disableInnerClassSerialization();

		return gsonBuilder;
	}
	
	/**
	 * 创建对象
	 * @return
	 */
	public static Gson create() {
		return GsonUtils.createCommonBuilder().create();
	}
}
