package com.hsh.baselib.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * FastJSON帮助工具类
 * 
 * @author Lich
 * 
 * 	2015-12-8 上午10:56:28
 * @version
 */
public abstract class FastJsonUtils {

	/**
	 * 输出值为null的字段
	 */
	public static final SerializerFeature WRITE_MAP_NULL_VALUE = SerializerFeature.WriteMapNullValue;

	/**
	 * 字符类型字段如果为null,输出为"",而非null
	 */
	public static final SerializerFeature WRITE_NULL_AS_EMPTY = SerializerFeature.WriteNullStringAsEmpty;

	/**
	 * Boolean字段如果为null,输出为false,而非null
	 */
	public static final SerializerFeature WRITE_NULL_BOOLEAN_AS_FALSE = SerializerFeature.WriteNullBooleanAsFalse;

	private static final SerializeConfig configMapping = new SerializeConfig();

	/**
	 * 默认格式化时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static {
		configMapping.put(Date.class, new SimpleDateFormatSerializer(DATE_FORMAT));
	}

	/**
	 * 防止实例化
	 */
	private FastJsonUtils() {

	}

	/**
	 * 将JSON格式的字符串转换成任意Java类型的对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param type
	 *            任意Java类型
	 * @return 任意Java类型的对象
	 */
	public static final <T> T parseObject(final String json, final TypeReference<T> type) {
		return JSON.parseObject(json, type);
	}
	
	/**
	 * 将JSON格式的字符串转换为java类型的对象或者java数组类型的对象，不包括java集合类型
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param clz
	 *            javaBean对象
	 * @return javaBean对象，不包括java集合类型的对象
	 */
	public static final <T> T parseObject(final String json, final Class<T> clz) {
		return JSON.parseObject(json, clz);
	}

	/**
	 * 将java类型的对象转换为JSON格式的字符串
	 * 
	 * @param object
	 *            java类型的对象
	 *            {@link #toJson(Object, SerializeConfig, SerializerFeature)}
	 * @return JSON格式的字符串，默认输出值为null的字段（WRITE_MAP_NULL_VALUE）
	 */
	public static final <T> String toJson(final T object) {
		return toJson(object, null);
	}

	/**
	 * 将java类型的对象转换为JSON格式的字符串
	 * 
	 * {@link #toJson(Object, SerializeConfig, SerializerFeature)}
	 * 
	 * @param object
	 *            java类型的对象
	 *            序列化输出JSON格式
	 * @return JSON格式的字符串，默认输出值为null的字段（WRITE_MAP_NULL_VALUE）
	 */
	public static final <T> String toJson(final T object, SerializerFeature serializerFeature) {
		if (null == serializerFeature) {
			serializerFeature = WRITE_MAP_NULL_VALUE;
		}
		return toJson(object, null, serializerFeature);
	}

	/**
	 * 将java类型的对象转换为JSON格式的字符串
	 * 
	 * @param object
	 *            java类型的对象
	 * @param serializerFeature
	 * @see FastJsonUtils#WRITE_MAP_NULL_VALUE
	 * @see FastJsonUtils#WRITE_NULL_AS_EMPTY
	 * @see FastJsonUtils#WRITE_NULL_BOOLEAN_AS_FALSE
	 * @return JSON格式的字符串
	 */
	public static final <T> String toJson(final T object, SerializeConfig config, SerializerFeature serializerFeature) {
		if (null == config) {
			config = configMapping;
		}
		if (null == serializerFeature) {
			serializerFeature = WRITE_MAP_NULL_VALUE;
		}
		return JSON.toJSONString(object, config, serializerFeature);
	}

	/**
	 * 将java类型的对象转换为JSON格式的字符串
	 * 
	 * {@link #toJson(Object, SerializeConfig, SerializerFeature)}
	 * 
	 * @param object
	 *            java类型的对象
	 * @param dateFormat
	 *            日期格式
	 * @return JSON格式的字符串
	 */
	public static final <T> String toDateJson(final T object, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		configMapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
		return toJson(object, configMapping, WRITE_MAP_NULL_VALUE);
	}

	/**
	 * 将JSON格式的字符串转换为List<T>类型的对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @param clz
	 *            指定泛型集合里面的T类型
	 * @return List<T>类型的对象
	 */
	public static final <T> List<T> toList(final String json, final Class<T> clz) {
		return JSON.parseArray(json, clz);
	}

	/**
	 * 将JSON格式的字符串转换为List<T>类型的对象
	 * 
	 * @param json
	 *            JSON格式的字符串
	 * @return List<T>类型的对象
	 */
	@SuppressWarnings("unchecked")
	public static final <T> List<T> toList(final String json) {
		Object o = JSON.parse(json);
		return (List<T>) o;
	}

	/**
	 * 将JSON格式的字符串转换为Map<T, N>类型的对象
	 * 
	 * @param json
	 * @return
	 */
	public static final <T, N> Map<T, N> toMap(final String json) {
		return parseObject(json, new TypeReference<Map<T, N>>() {});
	}

	/**
	 * json字符串转java实体对象
	 * @param json
	 * @param clz
	 * @param <T>
     * @return
     */
	public static final <T> T toBean(final String json,Class<T> clz){
		return parseObject(json,clz);
	}

}
