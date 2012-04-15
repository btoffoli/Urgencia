package br.com.btoffoli.activitys;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import br.com.btoffoli.R;
import br.com.btoffoli.service.NetService;

public class HelpActivity extends Activity {

	LocationManager lm = null;

	Location activityLocation = null;

	Boolean gps_enabled = false;

	Boolean network_enabled = false;

	Boolean status = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.help);

			Button btnEnviar = (Button) findViewById(R.id.btnSend);

			btnEnviar.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					try {
						enviar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});

		} catch (Exception e) {
			System.out.println("Excessão foi " + e.getMessage());
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		// return super.onCreateDialog(id);
		String msg = id > 0 ? "Ocorrência enviada com sucesso."
				: "Falha no envio da ocorrência.";

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Status de envio da ocorrência.").setMessage(msg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// when the user clicks the OK button
						// do something
						dialog.dismiss();
						if (id > 0)
							finish();
					}
				});
		return builder.create();

	}

	private void enviar() throws InterruptedException {
		/* tenta obter a posição do celular */
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {

		}
		try {
			network_enabled = lm
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {

		}

		System.out.println("gps_enabled = " + gps_enabled);
		// don't start listeners if no provider is enabled
		if (!gps_enabled && !network_enabled)
			return;

		LocationListener locListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				System.out.println("Obtendo posição do provider " + provider);
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub

			}

			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				lm.removeUpdates(this);
				activityLocation = location;
				try {
					new NetService().enviarOcorrencia(location);
						status = true;
						synchronized (lm) {
							lm.notify();
						}												
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};

		// if (gps_enabled)
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 50,
				locListener);
		lm.wait();
		
		showDialog(status ? 1 : 0);

	}

}
