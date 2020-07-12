package com.smn947.angelsdreams;

import android.app.*;
import android.os.*;
import android.webkit.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.loadUrl("https://angelsdream.com.co");
		
    }
}
