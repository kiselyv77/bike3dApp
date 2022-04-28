package com.example.bike3dapp.rcView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bike3dapp.R
import com.example.bike3dapp.databinding.ItemBinding
import com.example.bike3dapp.viewer.ModelActivity
import com.example.bike3dapp.viewer.Possitions

class Adapter(val activity: ModelActivity): RecyclerView.Adapter<Adapter.Holder>() {
    val list = listOf(
        ItemModel(
            "Снятие и установка колес",
            Possitions.FRONT_WHEEL,
            "wheel"
        ),
        ItemModel(
            "Настройка переключателей передач",
            Possitions.FRONT_SWITCH,
            "switch"
        ),
        ItemModel(
            "Обслуживание трансмиссии",
            Possitions.REAR_SWITCH,
            "transmission"
        ),
        ItemModel(
            "Обслуживание вилки",
            Possitions.FORK,
            "fork"
        ),
        ItemModel(
            "Обслуживание тормозов",
            Possitions.REAR_BRAKE,
            "brakes"
        ),

    )

    class Holder(chatItem:ItemBinding): RecyclerView.ViewHolder(chatItem.root){
        val title = chatItem.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val item = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = Holder(item)
        return holder

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = list[position].title
        holder.itemView.setOnClickListener {
            activity.scene!!.camera.setCameraPosition(list[position].position,true)
            val bundle = Bundle()
            bundle.putString("page", list[position].page)
            activity.mNavController!!.navigate(R.id.detailFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}






