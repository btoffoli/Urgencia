package br.com.btoffoli.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.location.Location;

public class NetService {
	public Boolean enviarOcorrencia(Location location) throws IOException {
		Boolean result = false;
		
		System.out.println("Location = " + location);
		
		String strLocation = "lat="+URLEncoder.encode(location.getLatitude() + "", "UTF-8") + 
				"&lon=" + URLEncoder.encode(location.getLongitude()+ "", "UTF-8");
//		String urlString = "http://192.168.1.129:3000/evento/receber?teste="+strLocation;
		String urlString = "http://10.10.10.103:3000/evento/receber?"+strLocation;

		
		URL url = new URL(urlString);

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		
		try {
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			// readStream(in);
			System.out.println(in.toString());
			result = true;			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();			
		} finally {
			urlConnection.disconnect();
			return result;
		}
	}
}
