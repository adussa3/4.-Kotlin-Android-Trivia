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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Create the drawerLayout variable
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Initialize the drawerLayout
        drawerLayout = binding.drawerLayout

        // Step 1: Find the navController from myNavHostFragment
        // Since we're using KTX, you can call "this.findNavController"
        val navController = this.findNavController(R.id.myNavHostFragment)

        // Step 2: Link the navController to the ActionBar
        // By calling NavigationUI.setupActionBarWithNavController
        // Add the drawerLayout menu as the 3rd parameter
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // In the anonymous function lock/unlock the drawer layout if the id matches the start destination (TitleFragment)
        // We only want to let the drawer layout side menu to slide out when the user is on the TitleFragment
        // Otherwise, lock the drawer layout so it doesn't slide
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        // Setup the NavigationUI to know about the navigation view
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    // Step 3: Find the navController and then call navController.navigateUp
    // This method handles the action to take when the up button (the left arrow in the actionbar) is pressed
    // IGNORE the comment above! We're replacing the up button
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        //return navController.navigateUp()

        // This replaces the up button with the navigation drawer button in the navigation UI
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}
