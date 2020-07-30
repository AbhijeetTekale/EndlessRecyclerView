package com.example.endlessrecyclerview

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentGateway : AppCompatActivity(),PaymentResultListener {
    val TAG = "myRazor"
    var price = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_gateway)
        val checkout =Checkout()
        var i = getIntent()
        price = i.getIntExtra("Price",0)
        checkout.setKeyID("rzp_test_TuIe2WbE1Mfzrn");
        startPayment()
    }
    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed ${p1}", Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful", Toast.LENGTH_SHORT).show()
    }
    private fun startPayment() {
        /*
        *  You need to pass current activity in order to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("amount",price)//pass amount in currency subunits

            val prefill = JSONObject()
            prefill.put("email","")
            prefill.put("contact","")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}
