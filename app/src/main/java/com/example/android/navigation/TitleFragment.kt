package com.example.android.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 * Use the [TitleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TitleFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for the title fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_title, container, false
        )

        // Complicated method to change fragments
        // NOTE: These require a view: View parameter
        /* binding.playButton.setOnClickListener {view: View ->
            // NOTE: these both do the same thing

            // This works if you import "androidx.navigation.Navigation"
            Navigation.findNavController(view).navigate(R.id.action_titleFragment_to_gameFragment)

            // This works if you import "androidx.navigation.findNavController"
            view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        } */

        // Simpler way to change fragments
        // This does NOT require a view: View parameter
        binding.playButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        )

        // return the created view
        return binding.root
    }
}