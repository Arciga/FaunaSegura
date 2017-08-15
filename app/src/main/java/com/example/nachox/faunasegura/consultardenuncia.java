package com.example.nachox.faunasegura;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by root on 14/08/17.
 */

public class consultardenuncia extends AppCompatActivity {
    WebView browser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultardenuncia);

        browser=(WebView)findViewById(R.id.webView);
        browser.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url); return true; } });
        browser.loadUrl("http://www.semarnatcam.campeche.gob.mx/procuraduria/sitio/consultadenuncia");}
}
