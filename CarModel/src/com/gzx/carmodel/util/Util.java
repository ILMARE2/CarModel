package com.gzx.carmodel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.listener.GetServerTimeListener;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 工具类
 * 
 * @author sunyudong1
 *
 */
public class Util {

	
	
	
	/*判断网络是否连接*/
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		   NetworkInfo nnNetworkInfo=mConnectivityManager.getActiveNetworkInfo();
		   if(nnNetworkInfo!=null){
			   return nnNetworkInfo.isAvailable();
		   }
		}

		return false;

	}
/**
 * 判断电话号码是否有效
 * @param phone
 * @return true有效 false 无效
 */
	public static boolean isPhoneNumberValid(String phone) {
		boolean isValid = false;
		//正则表达式
		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phone;
	Pattern pattern=Pattern.compile(expression);
	Matcher matcher=pattern.matcher(inputStr);
	if(matcher.matches()){
		isValid=true;
	}
	
		return isValid;
	}
}
