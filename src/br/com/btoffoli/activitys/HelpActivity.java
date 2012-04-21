package br.com.btoffoli.activitys;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import br.com.btoffoli.R;
import br.com.btoffoli.model.Ocorrencia;
import br.com.btoffoli.service.NetService;
import br.com.btoffoli.service.TelefoniaService;

public class HelpActivity extends Activity {

	LocationManager lm = null;
	
	Boolean gps_enabled = false;

	Boolean network_enabled = false;

	Boolean status = false;

	Ocorrencia ocorrencia;

	Thread currentSenderJob;

	Thread currentThreadOnChangeLocation;

	Boolean pararSenderJob = false;

	LocationListener locListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
			ocorrencia = new Ocorrencia();
			super.onCreate(savedInstanceState);

			setContentView(R.layout.help);

			Button btnEnviar = (Button) findViewById(R.id.btnSend);

			btnEnviar.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					try {

						currentSenderJob = new Thread(new Runnable() {
							public void run() {
								try {
									enviar();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});

						pararSenderJob = false;
						currentSenderJob.setName("currentSenderJob");
						currentSenderJob.setDaemon(true);
						if (!pararSenderJob)
							currentSenderJob.run();

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
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		if (id == 0) {
			String msg = status ? "Ocorrência enviada com sucesso."
					: "Falha no envio da ocorrência.";

			builder.setTitle("Status de envio da ocorrência.")
					.setMessage(msg)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// when the user clicks the OK button
									// do something
									dialog.dismiss();
									//finish();
								}
							});
		} else if (id == 1) {
			String msg = "Tentando enviar Ocorrência";
			builder.setTitle("Envio da ocorrência.")
					.setMessage(msg)
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.dismiss();
									pararTrabalhoEnvio();
								}
							});

		}
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

		if (!gps_enabled && !network_enabled){
			exibitMensagemTerminoStatusEnvio();
			return;
		}

		locListener = new LocationListener() {

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

			public void onLocationChanged(final Location location) {
				// TODO Auto-generated method stub
				lm.removeUpdates(this);
				if (!pararSenderJob) {
					currentThreadOnChangeLocation = new Thread(new Runnable() {
						public void run() {
							try {
								Ocorrencia o = capturarParametros();
								o.setLocalizacao(location);
								status = new NetService()
										.enviarOcorrencia(o);
							} catch (Exception e) {
								status = false;
								e.printStackTrace();
							} finally {
								liberarTela();
								exibitMensagemTerminoStatusEnvio();
							}
						}
					});
					currentThreadOnChangeLocation.setDaemon(true);
					currentThreadOnChangeLocation
							.setName("HandlerLocationThread");
					currentThreadOnChangeLocation.run();

				}
			}
		};
		
		// if (gps_enabled)
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 50,
				locListener);
		// lm.wait();

		travarTela();

	}

	private void travarTela() {
		showDialog(1);

	}

	private void liberarTela() {
		dismissDialog(1);
	}

	private void pararTrabalhoEnvio() {

		pararSenderJob = true;
		
		lm.removeUpdates(locListener);

		if (currentSenderJob != null && currentSenderJob.isAlive()) {

			currentSenderJob.interrupt();
			currentSenderJob = null;
		}

		

		if (currentThreadOnChangeLocation != null
				&& currentThreadOnChangeLocation.isAlive()) {

			currentThreadOnChangeLocation.interrupt();
			currentThreadOnChangeLocation = null;
		}

		exibitMensagemTerminoStatusEnvio();
	}
	
	private void exibitMensagemTerminoStatusEnvio(){
		showDialog(0);
	}
	
	private Ocorrencia capturarParametros(){
		Ocorrencia o = new Ocorrencia();
		o.setSolicitante( ((EditText) findViewById(R.id.editTextSolicitante)).getText().toString() );
		o.setNatureza( ((EditText) findViewById(R.id.editTextNatureza)).getText().toString() );
		o.setEstaNoLocal( ((CheckBox) findViewById(R.id.checkBoxNoLocal)).isChecked() );
		o.setContato(new TelefoniaService(this).getMyPhoneNumber());
		
		return o;
	}
	

}
