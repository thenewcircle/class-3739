package com.example.yamba_solution;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

	public final String TAG = this.getClass().getSimpleName();
	
    private Button buttonTweet;
	private EditText editStatus;
	private TextView textCount;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTweet = (Button) findViewById(R.id.buttonTweet);
        editStatus = (EditText) findViewById(R.id.editStatus);
        textCount = (TextView) findViewById(R.id.textCount);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	public void onTweetButtonClick(View view) {
		String text = editStatus.getText().toString();
		Log.i(TAG, "Sending Tweet: " + text);
		Toast toast = Toast.makeText(MainActivity.this, "Tweet Successful: " + text,
				Toast.LENGTH_LONG);
		toast.show();
	}
}
