    package com.example.vpndemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.VpnService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.LogUtils
import com.example.vpndemo.ui.ViewModel1
import com.example.vpndemo.ui.service.MyVpnService
import com.example.vpndemo.ui.service.MyVpnService.InnerBinder
import de.blinkt.openvpn.api.APIVpnProfile
import de.blinkt.openvpn.api.IOpenVPNAPIService
import de.blinkt.openvpn.api.IOpenVPNStatusCallback
import de.blinkt.openvpn.core.OpenVPNService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity()  {

    private val serviceIntent by lazy {
        Intent(this, MyVpnService::class.java)
    }

    private lateinit var openVpn:IOpenVPNAPIService


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            LogUtils.d("绑定开始")
            openVpn = IOpenVPNAPIService.Stub.asInterface(service)

            val intent = openVpn.prepare(applicationContext.packageName)

            //申请权限
            if (intent != null) {
                startActivityForResult(intent,1)
            }else{
                onActivityResult(1, RESULT_OK,null)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            LogUtils.d("结束绑定")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        lifecycleScope.launch(Dispatchers.IO) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                //当前状态是STARTED或者更高

            }else{
                //当前状态为CREATED 或者 INITIALIZED

            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val intent = Intent(IOpenVPNAPIService::class.java.name)
            intent.setPackage("com.example.vpndemo")
            bindService(intent,connection, Context.BIND_AUTO_CREATE)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {

        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 1) {
            LogUtils.d("获取权限成功，开始监听状态")
            openVpn.registerStatusCallback(object : IOpenVPNStatusCallback.Stub() {
                override fun newStatus(
                    uuid: String?,
                    state: String?,
                    message: String?,
                    level: String?
                ) {

                    LogUtils.d("状态==>$state")
                }


            })
            val inputStream = resources.openRawResource(R.raw.profile)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuffer = StringBuffer()
            var line:String?=reader.readLine()

            while ( line!= null) {
                stringBuffer.append("$line\n")
                line= reader.readLine()
                if (line==null) {
                    break
                }
            }

            LogUtils.d("配置文件==》${stringBuffer.toString()}")
            val profile = openVpn.addNewVPNProfile("zxy", false, stringBuffer.toString())
            openVpn.startProfile(profile.mUUID)
            openVpn.startVPN(stringBuffer.toString())
        }else{
            LogUtils.e("获取权限失败")
        }
    }


}