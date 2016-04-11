package com.gzx.carmodel;

import cn.bmob.v3.Bmob;
import android.app.Activity;
import android.os.Bundle;
/**
 * 所有activity基类
 * @author sunyudong1
 *
 */
public class BaseActivity extends Activity {

protected void onCreate(Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	 Bmob.initialize(this, "f5c619e89c22d83f4ada3348b001e32c");
	initView();
	setListener();
	initData();
}
/**
 * 填充数据
 */
protected void initData() {
	
	
}
/**
 * 设置监听
 */
protected void setListener() {

	
}
/**
 * 初始化view
 */
protected void initView() {
	
	
}
}
