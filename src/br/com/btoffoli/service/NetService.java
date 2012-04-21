package br.com.btoffoli.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import br.com.btoffoli.model.Ocorrencia;

import android.location.Location;

public class NetService {
	public Boolean enviarOcorrencia(Ocorrencia o) throws IOException {
		Boolean result = false;
		
		//String strLocation = "lat="+URLEncoder.encode(location.getLatitude() + "", "UTF-8") + 
		//		"&lon=" + URLEncoder.encode(location.getLongitude()+ "", "UTF-8");
//		String urlString = "http://192.168.1.129:3000/evento/receber?teste="+strLocation;
		String urlString = "http://10.10.10.103:3000/evento/receber";

		//+strLocation;

		
		URL url = new URL(urlString);

		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoOutput(true);
		urlConnection.setDoInput(true);
		urlConnection.setInstanceFollowRedirects(false); 
		urlConnection.setRequestMethod("POST"); 
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		urlConnection.setRequestProperty("charset", "utf-8");
		urlConnection.setUseCaches (false);
		
		
		try {
			/*Enviando os parametros da ocorrência*/			
			OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());			
			out.write(("?solicitante="+o.getSolicitante()).getBytes());
			out.write(("&natureza="+o.getNatureza()).getBytes());
			out.write(("&estaNoLocal="+o.getEstaNoLocal()).getBytes());
			out.write(("&long="+o.getLocalizacao().getLongitude()).getBytes());
			out.write(("&lat="+o.getLocalizacao().getLatitude()).getBytes());
			out.flush();			
			//urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(out.toString().getBytes().length));
			out.close();
			
			/*Recebendo resposta*/			
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			//readStream(in);
			if (in.toString().contains("OK")){
				result = true;
			}else {
				result = false;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = false;
		} finally {
			urlConnection.disconnect();
			return result;
		}
	}
}
