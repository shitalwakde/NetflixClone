package com.netflixclone.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.netflixclone.adapters.VideosAdapter
import com.netflixclone.databinding.FragmentDownloadsBinding
import com.netflixclone.network.models.VideoItem
import java.util.ArrayList

const val POSTER_IMAGE = "https://i.ibb.co/12fHwfg/netflix-downloads.png"

class DownloadsFragment : BottomNavFragment() {
    private lateinit var binding: FragmentDownloadsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
//        Glide.with(binding.posterImage).load(POSTER_IMAGE).into(binding.posterImage)

        val videoItems: MutableList<VideoItem> = ArrayList()
        val item = VideoItem()
        item.videoURL = "http://pixeldev.in/webservices/movie_app/movie_admin/movie_shorts/production%20ID_4812203.mp4"
        item.videoTitle = "Women In Tech"
        item.videoDesc = "International Women's Day 2019"
        videoItems.add(item)
        val item2 = VideoItem()
        item2.videoURL = "http://pixeldev.in/webservices/movie_app/movie_admin/movie_shorts/production%20ID_4444485.mp4"
        item2.videoTitle = "Sasha Solomon"
        item2.videoDesc = "How Sasha Solomon Became a Software Developer at Twitter"
        videoItems.add(item2)
        val item3 = VideoItem()
        item3.videoURL = "http://pixeldev.in/webservices/movie_app/movie_admin/movie_shorts/production%20ID_4434286.mp4"
        item3.videoTitle = "Happy Hour Wednesday"
        item3.videoDesc = " Depth-First Search Algorithm"
        videoItems.add(item3)
        val item4 = VideoItem()
        item4.videoURL = "http://pixeldev.in/webservices/movie_app/movie_admin/movie_shorts/production%20ID_4812203.mp4"
        item4.videoTitle = "Happy Hour Wednesday"
        item4.videoDesc = " Depth-First Search Algorithm"
        videoItems.add(item4)
        binding.viewPagerVideos.adapter = VideosAdapter(videoItems)
    }

    override fun onFirstDisplay() {
    }
}