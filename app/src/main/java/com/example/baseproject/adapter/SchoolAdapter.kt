package com.example.baseproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.baseproject.model.remote.data.SchoolResponse
import com.example.baseproject.databinding.RowSchoolBinding
import com.example.baseproject.view.data.School

/**
 * Adapter class for handling school list rendering
 */
class SchoolAdapter : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    inner class SchoolViewHolder(private val binding: RowSchoolBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(school: School) {
            binding.nameTextview.text = school.name
            binding.locationTextview.text = school.location
            binding.phoneTextview.text = school.phoneNumber
            binding.emailTextview.text = school.email
            binding.root.setOnClickListener{ onItemClickListener?.let { listener -> listener(school) } }
        }
    }

    /**
     * Differ callback to efficiently refresh lists
     */
    private val differCallback = object : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val binding = RowSchoolBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SchoolViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        val school = differ.currentList[position]
        holder.bind(school)
    }

    private var onItemClickListener: ((School) -> Unit)? = null

    fun setOnItemClickListener(listener: (School) -> Unit) {
        onItemClickListener = listener
    }
}