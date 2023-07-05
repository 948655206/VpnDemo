package com.example.vpndemo.ui.service

import android.app.Service
import android.content.Intent
import android.net.VpnService
import android.os.Binder
import android.os.IBinder
import android.os.ParcelFileDescriptor
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleRegistry
import com.blankj.utilcode.util.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket


class MyVpnService : VpnService() {


    val scope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.d("onCreate")

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.d("onStartCommand")
        //检测权限
        prepare(applicationContext)

        val builder = Builder()
        val vpn = builder.apply {
            addAddress("192.168.1.50", 24)
            addRoute("0.0.0.0", 0)
            addRoute("0:0:0:0:0:0:0:0", 0)
//            addDnsServer("8.8.8.8")
        }.establish()

        if (vpn != null) {
            LogUtils.d("创建网卡成功....")
//            receiveMsg(vpn)
            sendMsg(vpn)
        } else {
            LogUtils.e("开启服务失败...")
        }

        //关闭后重启
        return START_STICKY
    }

    val SERVER_IP = "192.168.1.20"
    val port = 7890
    val server by lazy {
        InetAddress.getByName(SERVER_IP)
    }

    fun receiveMsg(vpn: ParcelFileDescriptor) {
        val buff = ByteArray(1024)

        val fileOutputStream = FileOutputStream(vpn.fileDescriptor)


        scope.launch {

        }

    }

    private fun sendMsg(vpn: ParcelFileDescriptor) {
        scope.launch(Dispatchers.IO) {
            try {
                val socket = Socket()
                socket.bind(InetSocketAddress("192.168.1.50",7777))
                socket.connect(InetSocketAddress("192.168.1.20",7890))
                LogUtils.d("阻塞中..")

                //发送数据
                launch(Dispatchers.IO) {
                    try {
                        val inputStream = FileInputStream(vpn.fileDescriptor)
                        val buff = ByteArray(1024)
                        var len = 0
                        while (
                            inputStream.read(buff).also {
                                len = it
                            } != -1
                        ) {
                            val outputStream = socket.getOutputStream()
                            outputStream.write(buff, 0, len)
                        }
                    } catch (e: Exception) {
                        LogUtils.e("出错了socket123==》${e.toString()}")
                    }
//                    try {
//                        val inputStream = FileInputStream(vpn.fileDescriptor)
//                        val buff = ByteArray(1024)
//                        var len = 0
//                        while (
//                            inputStream.read(buff).also {
//                                len = it
//                            } != -1
//                        ) {
//                            val packet = DatagramPacket(
//                                buff, 0, len, server, port
//                            )
//                            socket.send(packet)
//                            len += buff.size
////                        LogUtils.d("读取到本地流量了...$len...${packet.address}")
//                        }
//                    } catch (e: Exception) {
//                        LogUtils.e("出错了socket123==》${e.toString()}")
//                    }

                }

                //接收数据
                launch(Dispatchers.IO) {
                    try {
                        while (!socket.isClosed) {
                            val buff = ByteArray(1024)
                            val outputStream = FileOutputStream(vpn.fileDescriptor)
                            val inputStream = socket.getInputStream()
                            var len=0
                            while (inputStream.read(buff).also {
                                    len = it
                                } != -1) {
                                outputStream.write(buff,0,len)
                            }
                        }
                    } catch (e: Exception) {
                        LogUtils.e("出错了socket321==》${e.toString()}")
                    }
//                    try {
//                        val outputStream = FileOutputStream(vpn.fileDescriptor)
//                        val buff = ByteArray(1024)
//                        while (!socket.isClosed) {
//                            val packet = DatagramPacket(buff, buff.size, server, 7890)
//                            packet.length = buff.size
//                            LogUtils.d("等待收到数据...")
//                            socket.receive(packet)
//                            LogUtils.d("收到数据了...")
//                            val data = packet.data
//                            outputStream.write(data, 0, packet.length)
//
//                        }
//                        LogUtils.e("socket被关闭了..")
//                    } catch (e: Exception) {
//                        LogUtils.e("出错了socket321==》${e.toString()}")
//                    }

                }

            } catch (e: Exception) {
                LogUtils.e("出现错误了==>${e.toString()}")
            }

        }
    }

    class InnerBinder : Binder() {

        fun bindSuccess() {
//             sayHello()
        }

        private fun sayHello() {
            LogUtils.i("绑定方法成功....")
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        LogUtils.d("onBind")
        return InnerBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        LogUtils.d("onUnbind")
        return super.onUnbind(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("onDestroy")
        //取消协程
        scope.cancel()

    }

}