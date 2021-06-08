package com.example.capstone.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.capstone.R
import com.example.capstone.databinding.FragmentFavouritesBinding


class FavouritesFragment : Fragment() {

    private lateinit var _binding : FragmentFavouritesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_favourites, container, false)
        return _binding.root
    }
}