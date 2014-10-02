package com.example.yamba_solution;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class MainActivity extends Activity {

	private static final int MAX_TWEET_SIZE = 140;

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

		// buttonTweet.setOnClickListener(new OnClickListener() {
		//     public void onClick(View v) {
		//         onTweetButtonClick(v);
		// }});

		editStatus.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void afterTextChanged(Editable s) {
				onTweetTextChanged(s);
			}
		});
		onTweetTextChanged(editStatus.getText());
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
		final String text = editStatus.getText().toString();
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			protected Boolean doInBackground(String... params) {  //Background Thread
				String text = params[0];
				try {
					YambaClient client = new YambaClient("student", "password");
					client.postStatus(text);
					Log.i(TAG, "Tweet Sent: " + text);
					return true;
				} catch (YambaClientException e) {
					Log.e(TAG, "Tweet Failed: " + text, e);
					return false;
				}
			}
			protected void onPreExecute() {  //UI thread before running
				super.onPreExecute();
				Toast toast = Toast.makeText(MainActivity.this,
						"Sending Tweet: " + text, Toast.LENGTH_LONG);
				toast.show();
			}
			protected void onPostExecute(Boolean result) {  //UI thread after running
				super.onPostExecute(result);
				String msg = result ? "Tweet Sent" : "Tweet Failed";
				Toast toast = Toast.makeText(MainActivity.this, msg,
						Toast.LENGTH_LONG);
				toast.show();
			}
		};
		task.execute(text);
	}

	public void onTweetTextChanged(Editable s) {
		int length = editStatus.getText().length();
		int sizeLeft = MAX_TWEET_SIZE - length;
		if (sizeLeft <= 0) {
			textCount.setTextColor(Color.RED);
		} else if (sizeLeft <= 30) {
			textCount.setTextColor(Color.YELLOW);
		} else {
			textCount.setTextColor(Color.GREEN);
		}
		String sizeLeftString = String.valueOf(sizeLeft);
		textCount.setText(sizeLeftString);
	}
}