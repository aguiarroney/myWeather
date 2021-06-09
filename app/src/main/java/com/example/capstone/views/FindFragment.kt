package com.example.capstone.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentFindBinding
import com.example.capstone.repository.Repository
import com.google.android.material.snackbar.Snackbar

class FindFragment : Fragment() {

    private lateinit var _binding: FragmentFindBinding
    private lateinit var _viewModel: FindFragmentViewModel
    private lateinit var _snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find, container, false)

        val repository = Repository()
        val factory = FindFragmentViewModelFactory(repository)
        _snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            "Missing Fields",
            Snackbar.LENGTH_INDEFINITE
        )

        _viewModel = ViewModelProvider(this, factory).get(FindFragmentViewModel::class.java)

        _binding.findViewModel = _viewModel

        _viewModel.fields.observe(viewLifecycleOwner, Observer {
            if (it) {
                _snackbar.setAction("Ok") {
                    _viewModel.clearFields()
                }.show()
            }
        })

        _binding.btnFind.setOnClickListener {
            _binding.etFindState.hideKeyboard()
            _viewModel.validateFields()
        }

        return _binding.root
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}