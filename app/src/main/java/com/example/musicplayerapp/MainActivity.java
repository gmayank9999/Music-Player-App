package com.example.musicplayerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    Button playPauseButton, stopButton, nextButton, prevButton;
    TextView songTitle;

    int[] songList = {
            R.raw.amplifier,
            R.raw.blue_eyes,
            R.raw.believer
    };

    String[] songNames = {
            "Amplifier",
            "Blue eyes",
            "Believer"
    };

    int currentSongIndex = 0;
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        playPauseButton = findViewById(R.id.playPauseButton);
        stopButton = findViewById(R.id.stopButton);
        nextButton = findViewById(R.id.nextButton);
        prevButton = findViewById(R.id.prevButton);
        songTitle = findViewById(R.id.songTitle);

        // Initialize the first song
        mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex]);
        songTitle.setText(songNames[currentSongIndex]);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        // If playing, pause the music and update the button text
                        mediaPlayer.pause();
                        playPauseButton.setText("▶");
                        isPlaying = false;
                    } else {
                        // If paused, start playing and update the button text
                        mediaPlayer.start();
                        playPauseButton.setText("⏸");
                        isPlaying = true;
                    }
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(MainActivity.this, songList[currentSongIndex]);
                    playPauseButton.setText("▶");
                    isPlaying = false;
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playNextSong();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPreviousSong();
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> playNextSong());
    }

    private void playNextSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        currentSongIndex = (currentSongIndex + 1) % songList.length;
        mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex]);
        songTitle.setText(songNames[currentSongIndex]);
        mediaPlayer.start();
        playPauseButton.setText("⏸");
        isPlaying = true;
        mediaPlayer.setOnCompletionListener(mp -> playNextSong());
    }

    private void playPreviousSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        currentSongIndex = (currentSongIndex - 1 + songList.length) % songList.length;
        mediaPlayer = MediaPlayer.create(this, songList[currentSongIndex]);
        songTitle.setText(songNames[currentSongIndex]);
        mediaPlayer.start();
        playPauseButton.setText("⏸");
        isPlaying = true;
        mediaPlayer.setOnCompletionListener(mp -> playNextSong());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
