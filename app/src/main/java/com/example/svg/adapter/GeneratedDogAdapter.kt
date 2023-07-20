package com.example.svg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.svg.databinding.GeneratedDogItemBinding
import com.example.svg.models.GenerateDogResponseModel
import java.util.LinkedList

class GeneratedDogAdapter(private val dogList: LinkedList<GenerateDogResponseModel> = LinkedList()): RecyclerView.Adapter<GeneratedDogAdapter.GeneratedDogViewHolder>() {
    inner class GeneratedDogViewHolder(private val binding: GeneratedDogItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItems(item: GenerateDogResponseModel) {
            Glide.with(binding.root).load(item.message).into(binding.ivGeneratedDogItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneratedDogViewHolder {
        val viewBinding = GeneratedDogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GeneratedDogViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onBindViewHolder(holder: GeneratedDogViewHolder, position: Int) {
        holder.bindItems(dogList[position])
    }

    fun updateList(list: LinkedList<GenerateDogResponseModel>) {
        dogList.clear()
        dogList.addAll(list)
        notifyDataSetChanged()
    }
}