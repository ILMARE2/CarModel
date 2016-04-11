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
	private LinearLayout ll_mLinearLayout; // ������--�Ӷ�����
	private TextView tv_versionNumber; // �汾��
	private String versionCode;

	protected void initView() {
		super.initView();
		setContentView(R.layout.activity_welcome);
		ll_mLinearLayout = (LinearLayout) findViewById(R.id.ll_mLinearLayout);
		tv_versionNumber = (TextView) findViewById(R.id.tv_versionNumber);
	}

	protected void initData() {
		// ��ʾ�汾��
		if ((versionCode = getVersionCode()) != null) {
			tv_versionNumber.setText("�汾�ţ�" + versionCode);

		}
		if(isNetWorkConnected()){
			//������ÿ�������
			setAnimation();
			
		}else{
			//���粻���ã������Ի���
			showNoNetworkDialog();
		}
		
		super.initData();
	}
  // ���ö���
	private void setAnimation() {
		// ���䶯��
		AlphaAnimation aa=new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		aa.setFillAfter(true);
		aa.setAnimationListener(new AnimationListener() {
			//������ʼ�¼�
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//�����ظ�ʱ�¼�
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			//�������Ž���
			@Override
			public void onAnimationEnd(Animation animation) {
				// ���뵽��½��ע�����
				Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		ll_mLinearLayout.startAnimation(aa);
		
	}
 //�ж������Ƿ����
	private boolean isNetWorkConnected() {
		//���ϵͳ�����ӷ���
		ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		//��ÿ��õ�������Ϣ
		NetworkInfo ni=cm.getActiveNetworkInfo();
		//���������Ƿ�����
		return (ni!=null&&ni.isConnected());
		
	}
//��ʾ��������ʧ�ܵĶԻ���
	private void showNoNetworkDialog() {
		//�����Ի���
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("��ʾ��");
		builder.setMessage("��ǰ�������Ӳ����ã�������������");
		//���õ�ǰ�Ի��򲻿���ȡ����Ҫô��ȷ����Ҫô��ȡ����ť
		builder.setCancelable(false);
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//��ϵͳ�������õĽ���
				if(android.os.Build.VERSION.SDK_INT > 10){
				    //3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
				    startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				}else{
					  startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					
				}
				
			}
		});
		//����ȡ����ť
				builder.setNegativeButton("ȡ��", new OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						//�ص���ǰҳ�棬�˳�Ӧ��
						finish();
					}
				});
				//��ʾ�Ի���
				builder.create().show();
	}

	// ��ð汾��
	private String getVersionCode() {
		PackageManager manager;
		try {

			// ��ȡ�汾��Ϣ
			manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
}
