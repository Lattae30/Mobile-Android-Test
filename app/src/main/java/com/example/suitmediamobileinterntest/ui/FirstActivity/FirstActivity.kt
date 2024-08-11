package com.example.suitmediamobileinterntest.ui.FirstActivity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.suitmediamobileinterntest.R
import com.example.suitmediamobileinterntest.databinding.ActivityFirstBinding
import com.example.suitmediamobileinterntest.ui.SecondActivity.SecondActivity

class FirstActivity : AppCompatActivity() {
    private var _binding: ActivityFirstBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnCheckPalindrome?.setOnClickListener {
            val tvPalindrome = binding?.palindromeEditText?.text.toString().trim().lowercase().replace("\\s".toRegex(), "")
            when{
                tvPalindrome.isEmpty() -> {
                    showToast(this, getString(R.string.empty_palindrome))
                } else -> {
                    val isPalindrome = checkPalindrome(tvPalindrome)
                    if (isPalindrome) showAlertDialog(getString(R.string.is_palindrome)) else showAlertDialog(getString(R.string.not_palindrome))
                }
            }
        }

        binding?.btnNext?.setOnClickListener {
            val tvName = binding?.nameEditText?.text.toString().trim()
            when {
                tvName.isNotEmpty() -> {
                    Intent(this@FirstActivity, SecondActivity::class.java).also {
                        it.putExtra(SecondActivity.EXTRA_NAME, tvName)
                        startActivity(it)
                    }
                } else -> {
                    showToast(this, getString(R.string.empty_name))
                }
            }
        }

    }

    // Check is Palindrome or not
    private fun checkPalindrome(text: String) : Boolean{
        val textReversed = text.reversed()
        return text == textReversed
    }

    private fun showAlertDialog(message: String){
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.palindrome_result))
            setMessage(message)
            setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}