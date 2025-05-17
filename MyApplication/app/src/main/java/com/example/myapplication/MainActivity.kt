package com.example.myapplication
import android.os.Bundle

import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    var score = 0
    private var gameActive = true
    var lastVisibleImage: ImageView? = null

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Timer'ı başlatmak için bir metod çağırabilirsin
        startTimer()

        // Görsellerin tıklanabilir olması için click listeners
        setImageViewListeners()
    }

    private fun startTimer() {
        timer?.cancel()
        val time = 10 // Saniye cinsinden
        timer = object : CountDownTimer((time * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textViewTime.text = "Time: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                endGame()
            }
        }
        timer?.start()
    }

    private fun endGame() {
        gameActive = false
        binding.textViewTime.text = "Time: 0"

        lastVisibleImage?.visibility = View.INVISIBLE
        lastVisibleImage?.isClickable = false

        // Oyun bittiğinde alert dialog göstermek
        AlertDialog.Builder(this)
            .setTitle("Game over")
            .setMessage("Your score: $score \n Do you want to play again?")
            .setPositiveButton("Restart") { _, _ ->
                restartGame()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()  // Exit the app
            }
            .setCancelable(false)
            .show()
    }

    private fun restartGame() {
        score = 0
        binding.textViewScore.text = "Score: 0"
        gameActive = true
        startTimer()

        // Görselleri tekrar görünür yap
        lastVisibleImage?.visibility = View.VISIBLE
        lastVisibleImage?.isClickable = true
        // Yeni bir rastgele görsel seç
        showRandomImage()
    }

    // Görsellerin tıklanabilir olmasını sağla
    private fun setImageViewListeners() {
        val imageViews = listOf(
            binding.civciv1,
            binding.civciv2,
            binding.civciv3,
            binding.civciv4,
            binding.civciv5,
            binding.civciv6,
            binding.civciv9,
            binding.civciv10,
            binding.civciv11,
            binding.civciv13,
            binding.civciv14,
            binding.civciv15
        )

        for (imageView in imageViews) {
            imageView.visibility = View.INVISIBLE
            imageView.isClickable = false

            // Görselleri tıklanabilir yapmak için listener ekliyoruz
            imageView.setOnClickListener {
                if (it == lastVisibleImage) { // Sadece seçili görsel tıklanabilir olacak
                    score++  // Puanı artır
                    binding.textViewScore.text = "Score: $score"

                    // Sonraki rastgele görseli seç
                    lastVisibleImage?.visibility = View.INVISIBLE
                    lastVisibleImage?.isClickable = false

                    showRandomImage() // Yeni bir görseli görünür yap
                }
            }
        }

        // Başlangıçta rastgele bir görsel seç ve tıklanabilir yap
        showRandomImage()
    }

    private fun showRandomImage() {
        val imageViews = listOf(
            binding.civciv1,
            binding.civciv2,
            binding.civciv3,
            binding.civciv4,
            binding.civciv5,
            binding.civciv6,
            binding.civciv9,
            binding.civciv10,
            binding.civciv11,
            binding.civciv13,
            binding.civciv14,
            binding.civciv15
        )

        // Rastgele bir görsel seç
        val randomImage = imageViews.random()
        randomImage.visibility = View.VISIBLE
        randomImage.isClickable = true // Seçilen görseli tıklanabilir yap
        lastVisibleImage = randomImage
    }
}
