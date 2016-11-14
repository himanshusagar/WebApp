package com.mc.webapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity implements OnTaskCompleted
{
    private Button button;
    private EditText editText;
    private WebView webView;

    private final HomeActivity object = this;

    private static final String TAG = "TAGG";
    private String url = "https://www.iiitd.ac.in/about";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);
        webView = (WebView) findViewById(R.id.webView);

        editText.setText(url);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webView.getSettings().setDefaultTextEncodingName("utf-8");

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                url = editText.getText().toString();
                new GetWebsite(webView, object).execute(url);


            }
        });

    }



    @Override
    public void onTaskCompleted(String response ,String mimeType ,String encoding)
    {
        Log.d(TAG , "inside listener" + response);

        Log.d(TAG , "mime" + mimeType + "...." +encoding);
        //webView.loadDataWithBaseURL(null, response , mimeType , "utf-8" , null);

        webView.loadData(response , mimeType , encoding);



    }

}
