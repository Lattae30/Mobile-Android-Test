package com.example.suitmediamobileinterntest.ui.ThirdActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmediamobileinterntest.databinding.ActivityThirdBinding
import com.example.suitmediamobileinterntest.retrofit.response.DataItem
import com.example.suitmediamobileinterntest.ui.SecondActivity.SecondActivity
import com.example.suitmediamobileinterntest.ui.ViewModelFactory
import com.example.suitmediamobileinterntest.ui.adapter.LoadingStateAdapter
import com.example.suitmediamobileinterntest.ui.adapter.UserAdapter

class ThirdActivity : AppCompatActivity() {
    private val viewModel by viewModels<ThirdActViewModel> {
        ViewModelFactory.getInstance()
    }
    private var _binding: ActivityThirdBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val layoutManager = LinearLayoutManager(this)
        binding?.rvUserList?.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUserList?.addItemDecoration(itemDecoration)

        binding?.toAppBar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })

        setUsersList()
    }

    private fun setUsersList(){
        val adapter = UserAdapter()
        binding?.rvUserList?.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter{
                adapter.retry()
            }
        )

        viewModel.getUsers().observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        binding?.swipeRefreshLayout?.setOnRefreshListener {
            adapter.refresh()
            binding?.swipeRefreshLayout?.isRefreshing = false

            viewModel.getUsers().observe(this) { pagingData ->
                adapter.submitData(lifecycle, pagingData)
            }
        }

        adapter.addLoadStateListener { loadState ->
            showLoading(binding!!.progressBar, loadState.source.refresh is LoadState.Loading)
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataItem) {
                Intent(this@ThirdActivity, SecondActivity::class.java).also{
                    it.putExtra(SecondActivity.EXTRA_SELECTED_USER, data.firstName)
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(progressBar: ProgressBar, isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
