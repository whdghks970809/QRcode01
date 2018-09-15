package com.example.user.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.alhazmy13.gota.Gota;
import net.alhazmy13.gota.GotaResponse;

import javax.xml.transform.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements Gota.OnRequestPermissionsBack, ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        new Gota.Builder(this)
                .withPermissions(Manifest.permission.CAMERA)
                .requestId(1)
                .setListener(this)
                .check();
    }

    @Override
    public void onRequestBack(int requestId, @NonNull GotaResponse gotaResponse) {

    }
    @Override
    public void onPause(){
        super.onPause();
        mScannerView.setResultHandler(this);
        mScannerView.stopCamera();
    }
    public void onResume(){
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(com.google.zxing.Result result) {
        mScannerView.resumeCameraPreview(this);
        mScannerView.stopCamera();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("결과값");
        builder.setMessage(result.toString());
        builder.setCancelable(false);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.startCamera();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mScannerView.startCamera();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
