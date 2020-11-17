package uz.test.testapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import uz.test.testapp.R
import uz.test.testapp.net.ApiClient
import uz.test.testapp.net.ApiService

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private val cd = CompositeDisposable()
    private val postAdapter = PostAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        apiService = ApiClient.client()
        setUpRv()
        loadData()
    }


    private fun setUpRv() {
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.adapter = postAdapter
    }


    private fun loadData() {
        apiService.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressBar.isVisible = false
                postAdapter.setItems(it)

            }, {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Connection error!",
                    Snackbar.LENGTH_SHORT
                )

            })
            .let { cd.add(it) }
    }


    override fun onDestroy() {
        super.onDestroy()
        cd.clear()
    }


}