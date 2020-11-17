package uz.test.testapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.test.testapp.R

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val posts = arrayListOf<Post>()

    fun setItems(items: List<Post>) {
        posts.clear()
        posts.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userIdText = itemView.findViewById<TextView>(R.id.userIdTxt)
        val title = itemView.findViewById<TextView>(R.id.headerTxt)
        val body = itemView.findViewById<TextView>(R.id.bodyTxt)

        fun bindData(post: Post) {
            userIdText.text = post.userId.toString()
            title.text = post.title
            body.text = post.body

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(posts[position])
    }
}