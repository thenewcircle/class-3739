package com.example.yamba_solution;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

/**
 * A placeholder fragment containing a simple view.
 */
public class TweetFragment extends Fragment {

	private static final int MAX_TWEET_SIZE = 140;

	public final String TAG = this.getClass().getSimpleName();

	private Button buttonTweet;
	private EditText editStatus;
	private TextView textCount;

	public TweetFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tweet, container,
				false);

		buttonTweet = (Button) rootView.findViewById(R.id.buttonTweet);
		editStatus = (EditText) rootView.findViewById(R.id.editStatus);
		textCount = (TextView) rootView.findViewById(R.id.textCount);

		buttonTweet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onTweetButtonClick(v);
			}
		});

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

		return rootView;
	}

	public void onTweetButtonClick(View view) {
		final String text = editStatus.getText().toString();
		AsyncTask<String, Void, Boolean> task = new AsyncTask<String, Void, Boolean>() {
			protected Boolean doInBackground(String... params) { // Background
																	// Thread
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

			protected void onPreExecute() { // UI thread before running
				super.onPreExecute();
				Activity activity = TweetFragment.this.getActivity();
				Toast toast = Toast.makeText(activity,
						"Sending Tweet: " + text, Toast.LENGTH_LONG);
				toast.show();
			}

			protected void onPostExecute(Boolean result) { // UI thread after
															// running
				super.onPostExecute(result);
				String msg = result ? "Tweet Sent" : "Tweet Failed";
				Activity activity = TweetFragment.this.getActivity();
				Toast toast = Toast.makeText(activity, msg,
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
