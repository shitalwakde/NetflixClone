package com.netflixclone.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.netflixclone.R
import com.netflixclone.data.FeedViewModel
import com.netflixclone.data.Injection
import com.netflixclone.databinding.ActivityBottomNavBinding


class BottomNavActivity : BaseActivity() {
    private lateinit var binding: ActivityBottomNavBinding

    // Flags to know whether bottom tab fragments are displayed at least once
    private val fragmentFirstDisplay = mutableListOf(false, false, false, false, false)

    private val feedFragment = FeedFragment()
    private val comingSoonFragment = ComingSoonFragment()
    private val downloadsFragment = DownloadsFragment()
    private val storiesFragment = StoriesFragment()
    private val moviesFragment = MoviesFragment()
    private val fragmentManager = supportFragmentManager
    private var activeFragment: Fragment = feedFragment

    private lateinit var feedViewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupViewModel()
    }

    private fun setupViewModel() {
        feedViewModel = ViewModelProvider(this, Injection.provideFeedViewModelFactory()).get(FeedViewModel::class.java)
    }

    private fun setupUI() {
        fragmentManager.beginTransaction().apply {
            add(R.id.container, downloadsFragment, "shorts").hide(downloadsFragment)
            add(R.id.container, comingSoonFragment, "news").hide(comingSoonFragment)
            add(R.id.container, storiesFragment, "stories").hide(storiesFragment)
            add(R.id.container, moviesFragment, "movies").hide(moviesFragment)
            add(R.id.container, feedFragment, "home")
        }.commit()


        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(feedFragment).commit()
                    activeFragment = feedFragment
                    true
                }
                R.id.stories -> {
                    if (!fragmentFirstDisplay[1]) {
                        fragmentFirstDisplay[1] = true
                        storiesFragment.onFirstDisplay()
                    }
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(storiesFragment).commit()
                    activeFragment = storiesFragment

                    true
                }
                R.id.shorts -> {
                    if (!fragmentFirstDisplay[2]) {
                        fragmentFirstDisplay[2] = true
                        downloadsFragment.onFirstDisplay()
                    }
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(downloadsFragment).commit()
                    activeFragment = downloadsFragment

                    true
                }
                R.id.news -> {
                    if (!fragmentFirstDisplay[3]) {
                        fragmentFirstDisplay[3] = true
                        comingSoonFragment.onFirstDisplay()
                    }
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(comingSoonFragment).commit()
                    activeFragment = comingSoonFragment

                    true
                }
                R.id.movies -> {
                    if (!fragmentFirstDisplay[4]) {
                        fragmentFirstDisplay[4] = true
                        moviesFragment.onFirstDisplay()
                    }
                    fragmentManager.beginTransaction().hide(activeFragment)
                        .show(moviesFragment).commit()
                    activeFragment = moviesFragment

                    true
                }
                else -> false
            }
        }
    }


    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FeedFragment) {
//            fragmentFirstDisplay[0] = true
//            fragment.onFirstDisplay()
        }
    }


    fun onFeedFragmentViewCreated(){
        if (!fragmentFirstDisplay[0]) {
            fragmentFirstDisplay[0] = true
            feedFragment.onFirstDisplay()
        }
    }

    fun onStoriesFragmentViewCreated(){
        if (!fragmentFirstDisplay[1]) {
            fragmentFirstDisplay[1] = true
            storiesFragment.onFirstDisplay()
        }
    }

    fun onMoviesFragmentViewCreated(){
        if (!fragmentFirstDisplay[4]) {
            fragmentFirstDisplay[4] = true
            moviesFragment.onFirstDisplay()
        }
    }
}