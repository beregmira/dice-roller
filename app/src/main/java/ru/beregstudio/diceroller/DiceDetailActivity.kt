package ru.beregstudio.diceroller

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ru.rustore.sdk.review.RuStoreReviewManagerFactory
import androidx.core.content.edit

/**
 * Activity for displaying a detailed view of a specific dice.
 *
 * This class allows the user to see a large version of a dice image
 * and interact with it using multitouch gestures for zooming.
 */
class DiceDetailActivity : AppCompatActivity() {
    private lateinit var detailImageView: ZoomableImageView
    private lateinit var sharedPref: SharedPreferences

    companion object {
        private const val LOG_TAG = "DiceRuStore"
        private const val PREF_KEY_HAS_REVIEWS = "has_reviewed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_detail)

        sharedPref = getSharedPreferences("ru.beregstudio.diceroller prefs", MODE_PRIVATE)

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

        // Show review form after 5 seconds
        showReviewFormAfterDelay()
    }

    private fun showReviewFormAfterDelay() {
        if (hasAlreadyReviewed()) {
            Log.d(LOG_TAG, "User already reviewed, skipping")
            return
        }
        Handler(Looper.getMainLooper()).postDelayed({
            Log.d(LOG_TAG, "RuStore worker started")
            try {
                val manager = RuStoreReviewManagerFactory.create(applicationContext)
                manager.requestReviewFlow().addOnSuccessListener { reviewInfo ->
                    Log.d(LOG_TAG, "RuStore form ready, launching form")
                    manager.launchReviewFlow(reviewInfo).addOnSuccessListener {
                        markAsReviewed()
                    }
                }.addOnFailureListener { error ->
                    Log.e(LOG_TAG, "RuStore flow failed: ${error.message}", error)
                }
            } catch (error: Exception) {
                Log.e(LOG_TAG, "RuStore init failed: ${error.message}", error)
            }
        }, 5000)
    }

    private fun hasAlreadyReviewed(): Boolean = sharedPref.getBoolean(PREF_KEY_HAS_REVIEWS, false)

    private fun markAsReviewed() {
        sharedPref.edit { putBoolean(PREF_KEY_HAS_REVIEWS, true) }
        Log.d(LOG_TAG, "Marked as reviewed")
    }
}