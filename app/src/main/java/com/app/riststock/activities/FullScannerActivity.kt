package com.app.riststock.activities

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.riststock.R
import com.app.riststock.classes.Constants
import com.app.riststock.databinding.ActivityFullScannerBinding
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode

class FullScannerActivity : ActivityBase() {


    private lateinit var codeScanner: CodeScanner
    var binding: ActivityFullScannerBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFullScannerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        title = ""
        val scannerView = binding!!.scannerView

        codeScanner = CodeScanner(this, scannerView)

        // Parameters (default values)

        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not


        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {


                    val sound = Uri.parse(
                        "android.resource://" + activiy.packageName
                            .toString() + "/" + R.raw.beep_04
                    )
                    val r = RingtoneManager.getRingtone(activiy, sound)
                    r.play()

                val intent = Intent()
                intent.putExtra(Constants.code,it.text)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast("Camera initialization error: ${it.message}")

            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}