package br.com.btoffoli.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.btoffoli.R;

public class MainActivity extends Activity implements OnClickListener{

	static final int PICK_CONTACT_REQUEST = 0;
	static final int CONTACT_REQUEST = 0;

	public enum opcoes {
		EVENTO, DISCAR_EMERGENCIA, ENVIAR_SMS
	}

	private opcoes opcao;

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.btnExit:
			finish();
			break;

		case R.id.btnHelp:
			opcao = opcoes.EVENTO;
			Intent i = new Intent(v.getContext(), HelpActivity.class);
			startActivity(i);
			break;
		}

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Button btnExit = (Button) findViewById(R.id.btnExit);
		btnExit.setOnClickListener(this);		

		final Button btnHelp = (Button) findViewById(R.id.btnHelp);
		btnHelp.setOnClickListener(this);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

		String answer = savedInstanceState.getString("answer");
		// This is a gratuitous test, remove it
		Object oldTaskObject = getLastNonConfigurationInstance();
		if (null != oldTaskObject) {
			int oldtask = ((Integer) oldTaskObject).intValue();
			int currentTask = getTaskId();
			// Task should not change across a configuration change
			assert oldtask == currentTask;
		}
		// Log.i(TAG, "onRestoreInstanceState"
		// + (null == savedInstanceState ? "" : RESTORE) + " " + answer);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CONTACT_REQUEST) {
			if (resultCode == RESULT_OK) {
				//TODO fazer alguma coisa...
			}
		}

	}

}