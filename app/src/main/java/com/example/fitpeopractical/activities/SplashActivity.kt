package com.example.fitpeopractical.activities
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.fitpeopractical.databinding.ActivitySplashBinding
import java.util.concurrent.TimeUnit


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(){

    private var handler: Handler? = null
    private var callback: Runnable? = null
    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        val view = splashBinding.root
        setContentView(view)
    }

    override fun onResume() {
        startNavigation()
        super.onResume()
    }

    private fun startNavigation(){

        handler = Handler(Looper.getMainLooper())
        callback = Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        handler!!.postDelayed(callback!!, TimeUnit.SECONDS.toMillis(3))
    }

    override fun onPause() {
        super.onPause()
        if(handler!=null) {
            handler!!.removeCallbacks(callback!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(handler!=null) {
            handler!!.removeCallbacks(callback!!)
        }
    }
}