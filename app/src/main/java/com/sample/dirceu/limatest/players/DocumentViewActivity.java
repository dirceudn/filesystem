package com.sample.dirceu.limatest.players;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.sample.dirceu.limatest.R;
import com.sample.dirceu.limatest.model.Node;
import com.sample.dirceu.limatest.util.Utility;

import java.util.Objects;

public class DocumentViewActivity extends AppCompatActivity {

    private static final String GOOGLE_DOCS = "https://docs.google.com/gview?embedded=true&url=";
    private WebView webViewDocument;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());


        attachUI();
        loadDocumentData();

    }

    private void loadDocumentData() {

        Bundle bundle = getIntent().getExtras();
        Node node = (Node) Objects.requireNonNull(bundle).getSerializable(Utility.DOCUMENT);
        webViewDocument.loadUrl(GOOGLE_DOCS
                + (node != null ? node.getUrl() : null));


    }

    @SuppressLint("SetJavaScriptEnabled")
    private void attachUI() {


        webViewDocument = findViewById(R.id.web_view_document);
        webViewDocument.getSettings().setJavaScriptEnabled(true);

    }


}
