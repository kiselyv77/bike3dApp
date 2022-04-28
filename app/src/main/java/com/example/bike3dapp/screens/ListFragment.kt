package com.example.bike3dapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bike3dapp.databinding.FragmentListBinding
import com.example.bike3dapp.rcView.Adapter
import com.example.bike3dapp.rcView.ItemModel
import com.example.bike3dapp.viewer.ModelActivity


class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentListBinding.inflate(inflater)
        val adapter = Adapter(activity as ModelActivity)
        binding.rcView.adapter = adapter
        binding.rcView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

}