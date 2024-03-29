package br.com.btoffoli.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class TelefoniaService {
	Context contexto;
	
	public TelefoniaService(Context context) {
		contexto = context;		
	}
	
	Boolean isWifi() {
		WifiManager wifiManager = (WifiManager) contexto
				.getSystemService(Context.WIFI_SERVICE);
		return (wifiManager.isWifiEnabled() && wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED);
	}

	Boolean is3g() {
		TelephonyManager phoneManager = (TelephonyManager) contexto
				.getSystemService(Context.TELEPHONY_SERVICE);

		return (phoneManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	Boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) contexto
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	public String getMyPhoneNumber() {
		TelephonyManager mTelephonyMgr;
		mTelephonyMgr = (TelephonyManager) contexto
				.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyMgr.getLine1Number();
	}

}
