package br.com.alura.firstchallenge

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.firstchallenge.api.Item
import br.com.alura.firstchallenge.databinding.RepositoryBinding


class RepositoryAdapter(
    private val context: Context
) : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private val lists = mutableListOf<Item>()

    class ViewHolder(binding: RepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        private val name = binding.repositoryName
        private val description = binding.repositoryDescription
        private val userName = binding.repositoryUsername
        private val nameSurname = binding.repositoryNameSurmane
        private val forksNumber = binding.repositoryForksNumber
        private val starRateNumber = binding.respositoryStarRateNumber

        fun vincula(list: Item) {
            name.text = list.name
            description.text = list.description
            userName.text = list.owner.login
            nameSurname.text = list.owner.id.toString()
            forksNumber.text = list.forks_count.toString()
            starRateNumber.text = list.stargazers_count.toString()

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = RepositoryBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = lists[position]
        holder.vincula(list)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun update(list: List<Item>) {
        this.lists.clear()
        this.lists.addAll(list)
        notifyDataSetChanged()

    }

}

