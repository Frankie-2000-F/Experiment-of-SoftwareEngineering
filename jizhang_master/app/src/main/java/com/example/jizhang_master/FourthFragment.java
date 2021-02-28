package com.example.jizhang_master;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class FourthFragment extends Fragment {
    private WebView mWebview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, null);
        mWebview= (WebView) view.findViewById(R.id.wv_web);
        //使用JAvascript语言
        mWebview.getSettings().setJavaScriptEnabled(true);
        //使用app打开网页
        mWebview.setWebViewClient(new MyWebViewClient());
        //设置网页组件功能
        mWebview.setWebChromeClient(new MyWebChromeClient());
        //设置链接
        mWebview.loadUrl("http://www.caijing.com.cn/");
        return view;
    }


    class MyWebViewClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        //在进入页面前的操作，这里为弹出alert框口。
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            }
        //页面结束后发生的操作
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

    }
    //设置后退键为返回上一步的操作
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode== KeyEvent.KEYCODE_BACK &&mWebview.canGoBack()){
//            mWebview.goBack();
//            return true;
//        }
//        return AppCompatActivity.onKeyDown(keyCode, event);
//    }

}
