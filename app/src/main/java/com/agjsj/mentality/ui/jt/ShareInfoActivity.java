package com.agjsj.mentality.ui.jt;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.agjsj.mentality.R;
import com.agjsj.mentality.ui.base.ParentWithNaviActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShareInfoActivity extends ParentWithNaviActivity {

    @Bind(R.id.wv_content)
    WebView wvContent;
    @Bind(R.id.progress)
    ProgressBar progress;
    private String shareUrl = null;
    private String from = "";

    @Override
    protected String title() {
        return "分享";
    }

    @Override
    public Object right() {
        return "来自" + from;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_info);
        ButterKnife.bind(this);

        Bundle bundle = getBundle();
        from = bundle.getString("from");
        shareUrl = bundle.getString("shareUrl");

        initNaviView();

        initWebView();


    }

    private void initWebView() {

        wvContent.getSettings().setDomStorageEnabled(true);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.getSettings().setAppCacheEnabled(true);
        wvContent.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wvContent.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });

        wvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progress.setProgress(newProgress);
                if (newProgress == 100) {
                    progress.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        wvContent.loadUrl(shareUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvContent != null)
            wvContent.destroy();
        ButterKnife.unbind(this);
    }

    // 覆盖onKeydown 添加处理WebView 界面内返回事件处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvContent.canGoBack()) {
            wvContent.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
