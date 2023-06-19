package com.etimaden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.etimaden.ugr_demo.R
import com.etimaden.util.*
import kotlinx.android.synthetic.main.layoutkurulum.*


class kurulum : AppCompatActivity() {

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
    }

    lateinit var downloadController: DownloadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This apk is taking pagination sample app
        // val apkUrl = "https://androidwave.com/source/apk/app-pagination-recyclerview.apk"
       
        val apkUrl = getIntent().getStringExtra("_OnlineUrl")//.replace("10.48.24.24","10.24.153.24")

        //val value: Any = String apkUrl = getIntent ().getStringExtra("_OnlineUrl").ToString();

Log.d("Adres",apkUrl.toString());

        downloadController = DownloadController(this, apkUrl)


            checkStoragePermission()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            // Request for camera permission.
            if (grantResults.size == 1 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // start downloading
                downloadController.enqueueDownload()
            } else {
                // Permission request was denied.

                kurulumlayout.showSnackbar(R.string.storage_permission_denied, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
            }
        }
    }


    private fun checkStoragePermission() {

        // Check if the storage permission has been granted
        if (checkSelfPermissionCompat(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {

            // start downloading
            downloadController.enqueueDownload()
        } else {
            // Permission is missing and must be requested.
            requestStoragePermission()
        }
    }

    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationaleCompat(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            kurulumlayout.showSnackbar(
                R.string.storage_access_required,
                com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestPermissionsCompat(
                    kotlin.arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
            }

        } else {
            requestPermissionsCompat(
                kotlin.arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }
}
