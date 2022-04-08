package br.com.alura.firstchallenge.requestScreen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.firstchallenge.apiPullRequest.RequestedReviewer
import br.com.alura.firstchallenge.apiPullRequest.User
import br.com.alura.firstchallenge.databinding.RequestBinding

class AdapterRequest(
    private val context: Context,
) : RecyclerView.Adapter<AdapterRequest.ViewHolder>() {

    private val listsRequest = mutableListOf<User>()

    class ViewHolder(binding: RequestBinding) : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.requestTitle
        private val body = binding.requestBody
        private val userName = binding.requestUsername
        private val fullName = binding.requestFullName

        fun binding(user: User){
            title.text = user.received_events_url
            body.text = user.type
            userName.text = user.id.toString()
            fullName.text = user.subscriptions_url

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RequestBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val user = listsRequest [position]
        holder.binding(user)
    }

    override fun getItemCount(): Int {
        return listsRequest.size
    }

    fun update (user: List<RequestedReviewer>){
        this.listsRequest.clear()
        this.listsRequest.addAll(user)
        notifyDataSetChanged()
    }
}

private fun <E> MutableList<E>.addAll(user: Collection<RequestedReviewer>) {

}






