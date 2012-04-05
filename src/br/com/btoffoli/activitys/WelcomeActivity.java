package br.com.btoffoli.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.btoffoli.R;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.welcome);  
		  
        final Button button = (Button) findViewById(R.id.welcome_ok_button);  
  
        button.setOnClickListener(new View.OnClickListener() {  
            public void onClick(View v) {  
                finish();  
            }  
        });  
	}

	
	
	
	
}
