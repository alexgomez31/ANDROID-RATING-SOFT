package com.example.ratingsoft.ui.model.Inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ratingsoft.databinding.FragmentNovedadesBinding




class NovedadesFragment : Fragment() {

    private var _binding: FragmentNovedadesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNovedadesBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }


}