package com.sdk_cli

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResult
import com.oppwa.mobile.connect.checkout.meta.CheckoutActivityResultContract
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.exception.PaymentError
import com.facebook.react.bridge.Promise



class MainActivity : ReactActivity() {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "SDK_CLI"

  public var  promise:Promise? = null

  
  protected val checkoutLauncher = registerForActivityResult(CheckoutActivityResultContract()) {
    result: CheckoutActivityResult -> this.handleCheckoutActivityResult(result)
    }

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

  protected fun createCheckoutSettings(checkoutId: String): CheckoutSettings {
        return CheckoutSettings(checkoutId, linkedSetOf("VISA", "MASTER" , "MADA"), Connect.ProviderMode.TEST)
    }

  public fun lunchCheckout(checkoutId: String ,  promise:Promise){
    this.promise = promise
    val checkoutSettings = createCheckoutSettings(checkoutId)
    checkoutLauncher.launch(checkoutSettings)
  }
 

 
 
    private fun handleCheckoutActivityResult(result: CheckoutActivityResult) {

    if (result.isCanceled) {
        promise?.reject("Canceled By User")
        return
    }

    if (result.isErrored) {
        val error: PaymentError? = result.paymentError
        error?.let { promise?.reject(it.errorMessage) }
        return
    }

    /* Transaction completed */
    val resourcePath = result.resourcePath
    promise?.resolve(resourcePath)
}

}
