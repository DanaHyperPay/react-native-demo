package com.sdk_cli

import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise



class HyperPay(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    // add to CalendarModule.kt
override fun getName() = "HyperPay"



@ReactMethod
fun startCheckout(checkoutid: String , promise:Promise) {
    val activity = getCurrentActivity() as MainActivity
    activity.lunchCheckout(checkoutid,promise)
}
}