package com.example.suitmediamobileinterntest.ui.SecondActivity

import com.example.suitmediamobileinterntest.ui.ThirdActivity.ThirdActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.suitmediamobileinterntest.R
import com.example.suitmediamobileinterntest.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private var _binding: ActivitySecondBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val selectedUser = intent.getStringExtra(EXTRA_SELECTED_USER)

        binding?.tvName?.text = name

        if (selectedUser != null) {
            binding?.tvSelectedUserName?.text = selectedUser
        } else{
            binding?.tvSelectedUserName?.text = getString(R.string.selected_user_name)
        }

        binding?.btnChooseUser?.setOnClickListener {
            startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
        }

        binding?.toAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_SELECTED_USER = "extra_selected_user"
    }
}