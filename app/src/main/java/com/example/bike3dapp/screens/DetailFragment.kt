package com.example.bike3dapp.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.example.bike3dapp.databinding.*
import com.example.bike3dapp.viewer.ModelActivity
import com.example.bike3dapp.viewer.Possitions


class DetailFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.back.setOnClickListener{
            (activity as ModelActivity).mNavController?.popBackStack()
        }
        val page = arguments?.getString("page")

        when(page){
            "wheel"-> {
                val data =  WheelsPageBinding.inflate(inflater)
                data.scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    Log.d(
                        "ScrollView", "$scrollY"
                    )
                    if(scrollY > 4332){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_WHEEL, true)
                    }
                    else{
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FRONT_WHEEL, true)
                    }

                })
                binding.linear.addView(data.root)
            }
            "switch"-> {
                val data =  SwitchPageBinding.inflate(inflater)
                data.scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    Log.d(
                        "ScrollView", "$scrollY"
                    )
                    if(scrollY > 6919){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_SWITCH, true)
                    }
                    else{
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FRONT_SWITCH, true)
                    }

                })
                binding.linear.addView(data.root)
            }

            "transmission"-> {
                val data =  TransmissionPageBinding.inflate(inflater)
                data.scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    Log.d(
                        "ScrollView", "$scrollY"
                    )

                    if(scrollY in 2812..13587){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.CHAIN, true)
                    }
                    else if(scrollY in 13587..17114){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_SWITCH, true)
                    }
                    else if(scrollY in 17114..23492){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FRONT_STAR, true)
                    }
                    else if(scrollY > 23492){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_STAR, true)
                    }
                    else{
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_SWITCH, true)
                    }

                })
                binding.linear.addView(data.root)
            }

            "fork" -> {
                val data =  ForkPageBinding.inflate(inflater)
                data.scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    Log.d(
                        "ScrollView", "$scrollY"
                    )

                    if(scrollY in 5199..8589){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.STEERING_CUP, true)
                    }
                    else if(scrollY in 8589..10205){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FORK_BOLT, true)
                    }
                    else{
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FORK, true)
                    }
                })

                binding.linear.addView(data.root)
            }

            "brakes" -> {
                val data =  BrakesPageBinding.inflate(inflater)
                data.scroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                    Log.d(
                        "ScrollView", "$scrollY"
                    )

                    if(scrollY in 3441..10265){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.FRONT_BRAKE, true)
                    }
                    else if(scrollY in 10265..14546){
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.STEERING_BRAKE, true)
                    }
                    else{
                        (activity as ModelActivity).scene?.camera?.setCameraPosition(Possitions.REAR_BRAKE, true)
                    }
                })

                binding.linear.addView(data.root)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as ModelActivity).scene!!.camera
            .setCameraPosition(
                arrayListOf(-1.2394733f, -36.25001f, 0.20980205f, 0.0f, 0.0f, 0.0f, -0.032034416f, 0.006879927f, 0.99946386f),
                true
            )
    }

}