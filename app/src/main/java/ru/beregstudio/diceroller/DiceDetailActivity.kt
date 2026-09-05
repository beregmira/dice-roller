package ru.beregstudio.diceroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for displaying a detailed view of a specific dice.
 *
 * This class allows the user to see a large version of a dice image
 * and interact with it using multitouch gestures for zooming.
 */
class DiceDetailActivity : AppCompatActivity() {
    private lateinit var detailImageView: ZoomableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_detail)

        // Now using the custom view that handles everything correctly
        detailImageView = findViewById(R.id.detailImageView)

        // Get image resource ID directly from Intent
        val imageResId = intent.getIntExtra("EXTRA_IMAGE_RES_ID", 0)

        if (imageResId != 0) {
            detailImageView.setImageResource(imageResId)
        }

        // Handle tap to close
        detailImageView.setOnClickListener {
            finish() // Close activity
        }
    }
}