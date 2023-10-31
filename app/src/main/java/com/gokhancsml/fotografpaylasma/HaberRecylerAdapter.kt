package com.gokhancsml.fotografpaylasma

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gokhancsml.fotografpaylasma.databinding.RecyclerRowBinding
import com.squareup.picasso.Picasso

class HaberRecylerAdapter(val postList: ArrayList<Post>) : RecyclerView.Adapter<HaberRecylerAdapter.PostHolder>() {

    inner class PostHolder(val binding: RecyclerRowBinding ) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val itemBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.binding.recyclerRowKullaniciEmail.text = postList[position].kullaniciEmail
        holder.binding.recyclerRowYorumText.text = postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.binding.recylerRowImageview)

    }

}