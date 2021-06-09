package com.example.capstone.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentFindBinding
import com.example.capstone.repository.Repository

class FindFragment : Fragment() {

    private lateinit var _binding: FragmentFindBinding
    private lateinit var _viewModel: FindFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find, container, false)

        val repository = Repository()
        val factory = FindFragmentViewModelFactory(repository)

        _viewModel = ViewModelProvider(this, factory).get(FindFragmentViewModel::class.java)

        _binding.btnFind.setOnClickListener {
            _viewModel.validateFields()
        }

        return _binding.root
    }
}