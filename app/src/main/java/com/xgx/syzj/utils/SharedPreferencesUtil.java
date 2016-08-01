package com.xgx.syzj.utils;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferencesUtil {
	private SharedPreferences sp;
	private Editor editor;
	private String name = "syzj";
	private int mode = Context.MODE_PRIVATE;

	public SharedPreferencesUtil(Context context) {
		this.sp = context.getSharedPreferences(name, mode);
		this.editor = sp.edit();
	}

	/**
	 * 创建一个工具类，默认打开名字为name的SharedPreferences实例
	 * 
	 * @param context
	 * @param name
	 *            唯一标识
	 * @param mode
	 *            权限标识
	 */
	public SharedPreferencesUtil(Context context, String name, int mode) {
		this.sp = context.getSharedPreferences(name, mode);
		this.editor = sp.edit();
	}

	/**
	 * 添加信息到SharedPreferences
	 * 
	 * @param name
	 * @param map
	 * @throws Exception
	 */
	public void add(Map<String, String> map) {
		Set<String> set = map.keySet();
		for (String key : set) {
			editor.putString(key, map.get(key));
		}
		editor.commit();
	}

	public void addString(String key, String value){
		if (TextUtils.isEmpty(key))
			return;
		editor.putString(key, value);
		editor.commit();
	}
	
	public void addInt(String key,int value){
		if (TextUtils.isEmpty(key)) {
			return;
		}
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 删除信息
	 * 
	 * @throws Exception
	 */
	public void deleteAll() throws Exception {
		editor.clear();
		editor.commit();
	}

	/**
	 * 删除一条信息
	 */
	public void delete(String key) throws Exception {
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 获取信息
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String get(String key) {
		if (sp != null) {
			return sp.getString(key, "");
		}
		return "";
	}
	
	public int getInt(String key){
		if (sp != null) {
			return sp.getInt(key, 0);
		}
		return 0;
	}

	/**
	 * 获取此SharedPreferences的Editor实例
	 * 
	 * @return
	 */
	public Editor getEditor() {
		return editor;
	}
}
