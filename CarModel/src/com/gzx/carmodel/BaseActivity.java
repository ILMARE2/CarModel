package com.gzx.carmodel;

import cn.bmob.v3.Bmob;
import android.app.Activity;
import android.os.Bundle;
/**
 * ����activity����
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
 * �������
 */
protected void initData() {
	
	
}
/**
 * ���ü���
 */
protected void setListener() {

	
}
/**
 * ��ʼ��view
 */
protected void initView() {
	
	
}
}
