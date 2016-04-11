package com.gzx.carmodel;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeActivity extends BaseActivity {
	private LinearLayout ll_mLinearLayout; // 根布局--加动画用
	private TextView tv_versionNumber; // 版本号
	private String versionCode;

	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_welcome);
		ll_mLinearLayout = (LinearLayout) findViewById(R.id.ll_mLinearLayout);
		tv_versionNumber = (TextView) findViewById(R.id.tv_versionNumber);
	}

	protected void initData() {
		// 显示版本号
		if ((versionCode = getVersionCode()) != null) {
			tv_versionNumber.setText("版本号：" + versionCode);

		}
		if(isNetWorkConnected()){
			//网络可用开启动画
			setAnimation();
			
		}else{
			//网络不可用，弹出对话框
			showNoNetworkDialog();
		}
		
		super.initData();
	}
  // 设置动画
	private void setAnimation() {
		// 渐变动画
		AlphaAnimation aa=new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		aa.setFillAfter(true);
		aa.setAnimationListener(new AnimationListener() {
			//动画开始事件
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//动画重复时事件
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//动画播放结束
			@Override
			public void onAnimationEnd(Animation animation) {
				// 进入到登陆与注册界面
				Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		ll_mLinearLayout.startAnimation(aa);
		
	}
 //判断网络是否可用
	private boolean isNetWorkConnected() {
		//获得系统的连接服务
		ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		//获得可用的网络消息
		NetworkInfo ni=cm.getActiveNetworkInfo();
		//返回网络是否连接
		return (ni!=null&&ni.isConnected());
		
	}
//显示连接网络失败的对话框
	private void showNoNetworkDialog() {
		//创建对话框
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("提示：");
		builder.setMessage("当前网络链接不可用，请检查网络设置");
		//设置当前对话框不可以取消，要么点确定，要么点取消按钮
		builder.setCancelable(false);
		builder.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//打开系统网络设置的界面
				if(android.os.Build.VERSION.SDK_INT > 10){
				    //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
				    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				}else{
					  startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					
				}
				
			}
		});
		//设置取消按钮
				builder.setNegativeButton("取消", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//关掉当前页面，退出应用
						finish();
					}
				});
				//显示对话框
				builder.create().show();
	}

	// 获得版本号
	private String getVersionCode() {
		PackageManager manager;
		try {

			// 获取版本信息
			manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
}
