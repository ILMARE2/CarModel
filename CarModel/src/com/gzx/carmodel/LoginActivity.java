package com.gzx.carmodel;

import java.util.regex.Pattern;

import cn.bmob.v3.listener.SaveListener;

import com.gzx.carmodel.bean.User;
import com.gzx.carmodel.util.PrefUtils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText et_username;
	private EditText et_password;
	private Button btn_login;
	private Button btn_register;
	private ProgressDialog dialog;

	@Override
	protected void initView() {

		super.initView();
		setContentView(R.layout.activity_login);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
	}

	@Override
	protected void initData() {
		// 初始化用户信息 获取上一次登陆的用户名和密码显示到界面上
		getUserInfoFromLastLogin();
		super.initData();
	}

	// 获取用户上次登陆时的信息，并且显示到界面上
	private void getUserInfoFromLastLogin() {
		et_password.setText(PrefUtils.getString(getApplicationContext(),
				"password", null));
		et_username.setText(PrefUtils.getString(getApplicationContext(),
				"username", null));

	}

	@Override
	protected void setListener() {
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		
		
		
		super.setListener();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login: // 切换登录，显示对话框
			showLoginDialog();
			login();
			break;
		case R.id.btn_register: // 切换到注册界面
			Intent intent = new Intent(getApplicationContext(),
					RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	// 登陆时显示的对话框
	private void showLoginDialog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("登录中..");
		dialog.show();

	}

	// 登陆
	private void login() {

		// 获取用户名和密码
		final String username = et_username.getText().toString();
		final String password = et_password.getText().toString();

		// 判断用户名是否为空
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(getApplicationContext(), "用户名不能为空!",
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}

		// 判断密码是否为空
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(getApplicationContext(), "密码不能为空!",
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}

		// 封装javabean
		final User user = new User();

		user.setUsername(username);
		user.setPassword(password);
		user.login(getApplicationContext(), new SaveListener() {

			@Override
			public void onSuccess() {
				// 记录用户名和密码
				PrefUtils.setString(getApplicationContext(), "username",
						user.getUsername());

				// 此处用user.getPassword()会报错;
				PrefUtils.setString(getApplicationContext(), "password",
						password);
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "登录成功！",
						Toast.LENGTH_SHORT).show();
				// 跳转到主界面
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "用户名或密码不正确！",
						Toast.LENGTH_LONG).show();
			}
		});

	}

}
