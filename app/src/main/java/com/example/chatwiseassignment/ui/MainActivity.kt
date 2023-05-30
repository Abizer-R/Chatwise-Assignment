package com.example.chatwiseassignment.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.chatwiseassignment.R
import com.example.chatwiseassignment.databinding.ActivityMainBinding
import com.example.chatwiseassignment.util.ConnectionLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG: String = MainActivity::class.simpleName + "_TESTING"

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private lateinit var connectionLiveData: ConnectionLiveData
    private var connectionStatus = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupInternetMonitoring()
    }

    private fun setupInternetMonitoring() {
        connectionLiveData = ConnectionLiveData(this)

        connectionLiveData.observe(this) { isConnected ->
            /** we need to check if it is not already true.
             * Otherwise, this will get triggered on activity's launch as well.
             */
            if(isConnected && connectionStatus == false) {
                Log.i(TAG, "AuthTesting: Internet Available")
                connectionStatus = true
                updateConnectionTextView(true)

            } else if(!isConnected){
                Log.i(TAG, "AuthTesting: Internet NOT Available")
                connectionStatus = false
                updateConnectionTextView(false)
            }
        }
    }

    private fun updateConnectionTextView(isConnected: Boolean) {
        binding.tvNoConnection.apply {
            setBackgroundColor(Color.parseColor( if(isConnected) "#419b45" else "#ED4134"))
            text = if(isConnected) "Back Online" else "No Connection"
            visibility = View.VISIBLE
        }
        if(isConnected) {
            lifecycleScope.launch(Dispatchers.IO) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    binding.tvNoConnection.visibility = View.GONE
                }
            }
        }
    }
}