package me.tcyrus.iodinessoandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;

import edu.tjhsst.tcyrus.iodine.SSO;

public class MainActivity extends ActionBarActivity {
	public final static String SSO_MESSAGE = "me.tcyrus.iodinessoandroid.SSOToken";
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			String url = SSO.SSOUrl(getString(R.string.app_name), new URL("http://1.1.1.1")).toString();
			Log.d("URL",url);
			webView = (WebView) findViewById(R.id.webView);
			webView.getSettings().setJavaScriptEnabled(true);
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					try {
						if (url.contains("1.1.1.1")) {
							String sso= Uri.parse(url).getQueryParameter("sso");
							Log.d("SSO Token",sso);
							Intent intent=new Intent(MainActivity.this,SSOActivity.class);
							intent.putExtra(SSO_MESSAGE,sso);
							startActivity(intent);
							return true;
						}
					} catch (Exception e) {return false;}
					return false;
				}
			});
			webView.loadUrl(url);
		} catch (Exception e) {Log.d("Exception",e.toString());}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
