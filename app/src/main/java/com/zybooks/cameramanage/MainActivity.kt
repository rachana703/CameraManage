package com.zybooks.cameramanage

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.zybooks.cameramanage.BackgroundTaskWorker
import com.zybooks.cameramanage.databinding.ActivityMainBinding
import com.zybooks.cameramanage.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)

        binding.btnPlaySound.setOnClickListener {
            playSound()
        }

        binding.btnStartAnimation.setOnClickListener {
            startAnimation()
        }

        binding.btnRunBackgroundTask.setOnClickListener {
            runBackgroundTask()
        }
    }

    private fun playSound() {
        mediaPlayer.start()
    }

    private fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.sample_animation)
        binding.imageView.startAnimation(animation)
    }

    private fun runBackgroundTask() {
        val request = OneTimeWorkRequest.from(BackgroundTaskWorker::class.java)
        WorkManager.getInstance(this).enqueue(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
