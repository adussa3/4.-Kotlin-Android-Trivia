/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        // From the gameWonFragment, go to the gameFragment
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        // Make a toast to check that the arguments were passed in successfully
        // Toast.makeText(context, "Num Questions: ${args?.numQuestions} Num Correct: ${args?.numCorrect}", Toast.LENGTH_LONG).show()

        // This tells Android that our fragment has an Options menu, so it will call onCreateOptionsMenu()
        setHasOptionsMenu(true)



        return binding.root
    }

    // Override onCreateOptionsMenu()
    // Use the passed-in MenuInflater to inflate the winner_menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.winner_menu, menu)

        // Check if the activity resolves
        // In other words, check to see if there aren't any compatible activities for the
        // implicit intent. If there aren't any compatible activities,
        // then hide the share menu item
        if (getShareIntent()?.resolveActivity(activity!!.packageManager) == null) {
            // hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share).setVisible(false)
        }
    }

    /**
     * This creates a "Share" implicit intent
     */
    private fun getShareIntent(): Intent? {
        // Get the arguments passed in from gameFragment
        // Get the number of questions and the number of questions answered correctly
        /*val args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
                   .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions))
        return shareIntent*/

        val args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        return activity?.let {
            ShareCompat.IntentBuilder.from(it)
                .setText(getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions))
                .setType("text/plain")
                .intent
        }
    }

    /**
     * Starts the "sharing" process
     */
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Override onOptionsItemSelected()
    // It will navigate to another app using the custom intent
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
