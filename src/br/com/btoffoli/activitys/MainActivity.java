package br.com.btoffoli.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import br.com.btoffoli.R;


public class MainActivity extends Activity {
	
	static final int PICK_CONTACT_REQUEST = 0;
	static final int CONTACT_REQUEST = 0;  
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
        
        
        final Button btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), HelpActivity.class);  
		        startActivity(i);
				
			}
		});
        
        //startActivity(new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED));
//        Intent i = new Intent(this, WelcomeActivity.class);  
//        startActivity(i); 
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
//		Log.i(TAG, "onRestoreInstanceState"
//		+ (null == savedInstanceState ? "" : RESTORE) + " " + answer);

	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if (requestCode == CONTACT_REQUEST) {  
			if (resultCode == RESULT_OK) {  
				// fazer alguma coisa...  
			}  
		}
		
	}
	
    
    
}