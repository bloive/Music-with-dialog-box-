package com.example.music

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_catalogue.*

class CatalogueActivity : AppCompatActivity() {
    private val catalogue =
        listOf("Antonio Lucio Vivaldi", "Ludwig van Beethoven", "Franz Joseph Haydn")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        for (composer in catalogue) {
            createComposer(composer)
        }
    }

    private fun createComposer(composerName: String) {
        val composer = layoutInflater.inflate(R.layout.composer, null)
        val image = composer.findViewById<ImageButton>(R.id.composerImage)
        val name = composer.findViewById<TextView>(R.id.composerName)

        val composerId =
            composerName.substring(composerName.lastIndexOf(" ") + 1).toLowerCase() + "_image"

        val imageId = resources.getIdentifier(composerId, "drawable", packageName)
        image.setImageResource(imageId)


        image.setOnClickListener {
            //TODO toasti ar chans
//            Toast.makeText(this, "you clicked", Toast.LENGTH_SHORT).show()
//            Log.i("music", "toast")
            showComposerInfo(composerName)
        }
        name.text = composerName
        catalogue_grid.addView(composer)
    }

    private fun showComposerInfo(composerName: String) {
        val infoId =
            composerName.substring(composerName.lastIndexOf(" ") + 1).toLowerCase() + "_info"
        val textFileId = resources.getIdentifier(infoId, "raw", packageName)
        //TODO exception handleri ar unda aq bufferedReaders?
        val fileText = resources.openRawResource(textFileId).bufferedReader().readText()
        val mp3File =
            composerName.substring(composerName.lastIndexOf(" ") + 1).toLowerCase() + "_music"
        val mp3FileId = resources.getIdentifier(mp3File, "raw", packageName)
        val mp = MediaPlayer.create(this, mp3FileId)
        mp.start()


        val builder = AlertDialog.Builder(this)
        builder.setTitle("$composerName")
        builder.setMessage(fileText)
        builder.setPositiveButton("OK") { _, _ ->
            mp.stop()
        }
        val dialog = builder.create()
        dialog.show()
    }
}