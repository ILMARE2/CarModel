package com.gzx.carmodel;



import java.util.regex.Pattern;

import cn.bmob.v3.listener.SaveListener;

import com.gzx.carmodel.bean.User;
import com.gzx.carmodel.bean.UserInfo;
import com.gzx.carmodel.util.Util;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ע�����
 * @author sunyudong1
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private Button btnReg;          //ע�ᰴť
	private EditText etUsername;    //�û���
	private EditText etPassword;    //����
	private EditText etComfirmPsd;  //ȷ������
	private EditText etPhone;       //�绰����

	private String username = null;
	private String password = null;
	private String comfirmPsd = null;  
	private String phone = null;
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_register);
		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		etComfirmPsd = (EditText) findViewById(R.id.et_comfirm_psd);
		etPhone = (EditText) findViewById(R.id.et_phone);
		btnReg = (Button) findViewById(R.id.btn_reg_now);
		super.initView();
	}
	
	@Override
	protected void setListener() {
		btnReg.setOnClickListener(this);
		// �ı���ʧȥ����
				this.etUsername.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String name = etUsername.getText().toString();
							String pattern = "[a-zA-Z0-9]{6,12}";
							if (Pattern.matches(pattern, name) == false) {
				CharSequence html1 = Html.fromHtml("<font color='red'>�û�����ʽ����</font>"); 
								etUsername.setError(html1);
								// editLoginName.setText("");
								// ѡ�����������
								etUsername.selectAll();
								return;
							}
						}
					}
				});
				this.etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String name = etPassword.getText().toString();
							String pattern = "[a-zA-Z0-9]{6,25}";
							if (Pattern.matches(pattern, name) == false) {
								CharSequence html1 = Html.fromHtml("<font color='red'>�����ʽ����</font>");
								etPassword.setError(html1);
								// editLoginName.setText("");
								// ѡ�����������
								etPassword.selectAll();
								return;
							}
						}
					}
				});
				this.etComfirmPsd.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String name = etPassword.getText().toString();
							if(!(name.equals(etComfirmPsd.getText().toString()))){
								CharSequence html1 = Html.fromHtml("<font color='red'>�����������벻��ͬ</font>");
								etComfirmPsd.setError(html1);
								etPassword.selectAll();
								etComfirmPsd.selectAll();
								return;
							}
						}
					}
				});
				
		this.etPhone.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
				String phone = etPhone.getText().toString();
				if(!Util.isPhoneNumberValid(phone)){
					CharSequence html1 = Html.fromHtml("<font color='red'>�ֻ������벻��ȷ</font>");
					etPhone.setError(html1);
					etPhone.selectAll();
					return;
				}
				}
			}
		});
		
		
		super.setListener();
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.btn_reg_now:
			username = etUsername.getText().toString();
			password = etPassword.getText().toString();
			comfirmPsd = etComfirmPsd.getText().toString();
			phone = etPhone.getText().toString();
		if(!Util.isNetworkConnected(this)){
			toast("û�����磬������������");
		}else if(username.equals("") || password.equals("")
				|| comfirmPsd.equals("") || phone.equals("")){
			toast("�뽫��Ϣ��д������");
		} else if (!comfirmPsd.equals(password)) {
			toast("�����������벻һ��");
		} else if(!Util.isPhoneNumberValid(phone)) {
			toast("��������ȷ���ֻ�����");
		}else{
			//�ύע����Ϣ
			User u=new User();
		
			u.setUsername(username);
			u.setPassword(password);
			
			//u.setMobilePhoneNumber(phone);
			//�û�ע��,������dmob���Ʒ����ṩ�ķ�����������������user���в���һ������
			//���� �ɹ�����onSuccess����   ʧ�ܵ���onFailure������

		u.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				saveUserInfo();
				
			}
			
			

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("�û����Ѵ��ڣ��������ְ�");
				
			}
		});
		}
		break;
  default:break;   
		
		}
	}
	 protected void saveUserInfo() {
			//�����û���Ϣ
			UserInfo userinfo=new UserInfo();
			userinfo.setPhone(phone);
			userinfo.setUserName(username);
			
			userinfo.save(getApplicationContext(), new SaveListener() {
				
				public void onSuccess() {
					toast("ע��ɹ�����ʼ�����ɣ�");
					Intent backLogin = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(backLogin);
					RegisterActivity.this.finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
				}
			});
		
	}
	private void toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();;
		
	}
}
