package com.example.fitpeopractical.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitpeopractical.R
import com.example.fitpeopractical.databinding.ItemPhotoListBinding
import com.example.fitpeopractical.models.PhotoListItem
import com.squareup.picasso.Picasso

class PhotoListAdapter(private val photoList :List<PhotoListItem>) : RecyclerView.Adapter<PhotoListAdapter.PhotoListHolder>(){

    class PhotoListHolder(val view: ItemPhotoListBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListHolder {
        return PhotoListHolder(
            ItemPhotoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: PhotoListHolder, position: Int) {
        val photoData = photoList[position]
        holder.view.tvDescription.text = photoData.title

        Picasso.get()
            .load(photoData.thumbnailUrl)
            .placeholder(R.drawable.ic_photo_thumb)
            .error(R.drawable.ic_photo_thumb)
            .into(holder.view.imgThumb)
    }
}