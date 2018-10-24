package domine.domino

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val BLUETOOTH_ENABLE_REQUEST = 1
    @SuppressLint("AddJavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        val webview = wv
        webview.settings.javaScriptEnabled = true
        webview.settings.userAgentString = "Mozilla/5.0 (Linux; Android 6.0.1; SM-G532G Build/MMB29T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.83 Mobile Safari/537.36"
        val activity = this
        webview.webViewClient = object: WebViewClient() {
            override fun onReceivedError(view:WebView, errorCode:Int, description:String, failingUrl:String) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            override fun onReceivedError(view:WebView, req: WebResourceRequest, rerr: WebResourceError) {
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString())
            }
        }

        webview.loadUrl("https://dominoes.playdrift.com")

        val bluetooth:BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if(bluetooth!=null){
            bluetoothButton.setOnClickListener {

                if(bluetooth.isEnabled){
                    //Toast.makeText(this,"bluetooth yanilidir artiq",Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_STREAM,"salamlar")
                    startActivity(intent)


                    val pm = getPackageManager()
                    val appsList = pm.queryIntentActivities(intent, 0)
                    if (appsList.size > 0){
                        var packageName:String? = null
                        var className:String? = null
                        var found = false
                        for (info in appsList)
                        {
                            packageName = info.activityInfo.packageName
                            if (packageName == "com.android.bluetooth")
                            {
                                className = info.activityInfo.name
                                found = true
                                break
                            }
                        }


                        if (!found)
                        {
                            Toast.makeText(this, "bluetooth not found in list",
                                    Toast.LENGTH_SHORT).show()
                            // exit
                        }else{
                            intent.setClassName(packageName, className)
                            startActivity(intent)
                        }
                    }
                }else {
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(intent, BLUETOOTH_ENABLE_REQUEST)
                }


            }
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data!=null){
            when(requestCode){
                BLUETOOTH_ENABLE_REQUEST->{
                    bluetoothButton.callOnClick()
                }
            }
        }
    }
}
