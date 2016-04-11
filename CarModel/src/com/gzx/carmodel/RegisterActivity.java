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
 * 注册界面
 * @author sunyudong1
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	private Button btnReg;          //注册按钮
	private EditText etUsername;    //用户名
	private EditText etPassword;    //密码
	private EditText etComfirmPsd;  //确认密码
	private EditText etPhone;       //电话号码

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
		// 文本框失去焦点
				this.etUsername.setOnFocusChangeListener(new OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							String name = etUsername.getText().toString();
							String pattern = "[a-zA-Z0-9]{6,12}";
							if (Pattern.matches(pattern, name) == false) {
				CharSequence html1 = Html.fromHtml("<font color='red'>用户名格式错误</font>"); 
								etUsername.setError(html1);
								// editLoginName.setText("");
								// 选中输入的内容
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
								CharSequence html1 = Html.fromHtml("<font color='red'>密码格式有误</font>");
								etPassword.setError(html1);
								// editLoginName.setText("");
								// 选中输入的内容
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
								CharSequence html1 = Html.fromHtml("<font color='red'>两次密码输入不相同</font>");
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
					CharSequence html1 = Html.fromHtml("<font color='red'>手机号输入不正确</font>");
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
			toast("没有网络，请检查网络链接");
		}else if(username.equals("") || password.equals("")
				|| comfirmPsd.equals("") || phone.equals("")){
			toast("请将信息填写完整！");
		} else if (!comfirmPsd.equals(password)) {
			toast("两次密码输入不一致");
		} else if(!Util.isPhoneNumberValid(phone)) {
			toast("请输入正确的手机号码");
		}else{
			//提交注册信息
			User u=new User();
		
			u.setUsername(username);
			u.setPassword(password);
			
			//u.setMobilePhoneNumber(phone);
			//用户注册,这里是dmob的云服务提供的方法，即往服务器的user表中查入一条数据
			//插入 成功调用onSuccess方法   失败调用onFailure方法。

		u.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				saveUserInfo();
				
			}
			
			

			@Override
			public void onFailure(int arg0, String arg1) {
				toast("用户名已存在，换个名字吧");
				
			}
		});
		}
		break;
  default:break;   
		
		}
	}
	 protected void saveUserInfo() {
			//保存用户信息
			UserInfo userinfo=new UserInfo();
			userinfo.setPhone(phone);
			userinfo.setUserName(username);
			
			userinfo.save(getApplicationContext(), new SaveListener() {
				
				public void onSuccess() {
					toast("注册成功，开始工作吧！");
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
