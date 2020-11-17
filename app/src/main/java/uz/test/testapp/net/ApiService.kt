package uz.test.testapp.net


import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import uz.test.testapp.ui.Post

interface ApiService {
    @GET("posts")
    fun getPosts(): Single<List<Post>>
}