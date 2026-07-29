package com.example.cashtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class StockNewsFragment extends Fragment {

    private WebView webView;
    private Spinner spinner;

    public StockNewsFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_news, container, false);

        webView = view.findViewById(R.id.webView_stock_news);
        spinner = view.findViewById(R.id.spinner_category);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        String[] categories = {"Stock Market", "Crypto", "Gold", "Car Industry"};
        String[] urls = {
                "https://www.marketwatch.com/",
                "https://www.coinbase.com/explore",
                "https://www.cnn.com/markets/stocks/gold",
                "https://www.cnn.com/markets/stocks/CAR"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                webView.loadUrl(urls[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinner.setSelection(0);

        return view;
    }
}
