package com.sms

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import org.json.JSONArray
import org.json.JSONObject

class SmsModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

     companion object {
        const val REQUEST_CODE_READ_SMS = 1
    }

    override fun getName(): String {
        return "SmsModule"
    }

  
  @ReactMethod
  fun multiply(a: Double, b: Double, promise: Promise) {
    promise.resolve(a * b)
  }

  @ReactMethod
    fun checkAndRequestSmsPermission(promise: Promise) {
        val activity: Activity? = currentActivity
        if (activity == null) {
            promise.reject("Activity is null")
            return
        }

        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_SMS) 
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                activity, 
                arrayOf(android.Manifest.permission.READ_SMS), 
                REQUEST_CODE_READ_SMS
            )
        } else {
            // Permission already granted
            promise.resolve(true)
        }
    }

     @ReactMethod
    fun getSms(promise: Promise) {
        val activity: Activity? = currentActivity
        if (activity == null) {
            promise.reject("Activity is null")
            return
        }

        // Check if the permission is granted
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_SMS) 
                != PackageManager.PERMISSION_GRANTED) {
            promise.reject("Permission not granted")
            return
        }

        try {
            val smsList = JSONArray()
            val cursor = activity.contentResolver.query(
                Uri.parse("content://sms/inbox"), 
                null, 
                null, 
                null, 
                null
            )

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val sms = JSONObject()
                    sms.put("address", cursor.getString(cursor.getColumnIndexOrThrow("address")))
                    sms.put("body", cursor.getString(cursor.getColumnIndexOrThrow("body")))
                    sms.put("date", cursor.getString(cursor.getColumnIndexOrThrow("date")))
                    smsList.put(sms)
                } while (cursor.moveToNext())
            }

            cursor?.close()

            promise.resolve(smsList.toString())
        } catch (e: Exception) {
            promise.reject("Failed to read SMS", e)
        }
    }

}
