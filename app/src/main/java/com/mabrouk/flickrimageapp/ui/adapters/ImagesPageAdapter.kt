package com.mabrouk.flickrimageapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mabrouk.flickrimageapp.R
import com.mabrouk.flickrimageapp.data.models.ImageModel
import com.mabrouk.flickrimageapp.databinding.ImageItemViewBinding


/*
* Created By mabrouk on 28/08/19
* FlickrImage App
*/

class ImagesPageAdapter(val lisetner:ImagesCallBack) : PagedListAdapter<ImageModel,ImagesPageAdapter.Holder>(callBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val viewBinding = DataBindingUtil.inflate<ImageItemViewBinding>(LayoutInflater.from(parent.context),
            R.layout.image_item_view,parent,false)
        return Holder(viewBinding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
      holder.bind(getItem(position)!!)
    }

    inner class Holder(var viewBinding: ImageItemViewBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(model:ImageModel){
            viewBinding.imageModel=model
            viewBinding.root.setOnClickListener {
                lisetner.onclickImage(model)
            }
        }
    }

    companion object{
        val callBack = object :DiffUtil.ItemCallback<ImageModel>(){
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean = oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
                oldItem == newItem

        }
    }

    public interface ImagesCallBack{
        fun onclickImage(item:ImageModel)
    }
}