package com.android.smartfarm.ui.activity

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.smartfarm.R
import com.android.smartfarm.data.repositories.SocketModule
import com.android.smartfarm.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    @Inject lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        //서버 연결
        val navmainFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment//바텀네비게이터 설정
        val navController = navmainFragment.navController
        binding.mainBottomNav.setupWithNavController(navController)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->//fcm 토큰 서버저장
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            val msg = token.toString()
            socket.emit("fcm",msg)
        })
    }


}