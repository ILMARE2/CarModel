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
		// ��ʼ���û���Ϣ ��ȡ��һ�ε�½���û�����������ʾ��������
		getUserInfoFromLastLogin();
		super.initData();
	}

	// ��ȡ�û��ϴε�½ʱ����Ϣ��������ʾ��������
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
		case R.id.btn_login: // �л���¼����ʾ�Ի���
			showLoginDialog();
			login();
			break;
		case R.id.btn_register: // �л���ע�����
			Intent intent = new Intent(getApplicationContext(),
					RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	// ��½ʱ��ʾ�ĶԻ���
	private void showLoginDialog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage("��¼��..");
		dialog.show();

	}

	// ��½
	private void login() {

		// ��ȡ�û���������
		final String username = et_username.getText().toString();
		final String password = et_password.getText().toString();

		// �ж��û����Ƿ�Ϊ��
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(getApplicationContext(), "�û�������Ϊ��!",
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}

		// �ж������Ƿ�Ϊ��
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(getApplicationContext(), "���벻��Ϊ��!",
					Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}

		// ��װjavabean
		final User user = new User();

		user.setUsername(username);
		user.setPassword(password);
		user.login(getApplicationContext(), new SaveListener() {

			@Override
			public void onSuccess() {
				// ��¼�û���������
				PrefUtils.setString(getApplicationContext(), "username",
						user.getUsername());

				// �˴���user.getPassword()�ᱨ��;
				PrefUtils.setString(getApplicationContext(), "password",
						password);
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "��¼�ɹ���",
						Toast.LENGTH_SHORT).show();
				// ��ת��������
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "�û��������벻��ȷ��",
						Toast.LENGTH_LONG).show();
			}
		});

	}

}
