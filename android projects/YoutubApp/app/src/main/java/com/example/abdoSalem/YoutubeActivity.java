package com.example.abdoSalem;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    static final String GOOGLE_API_KEY = "AIzaSyCM8nR6NWVIvI9H2Y0UmmmyFwK51XUseik";
    static final String YOUTUBE_VIDEO_ID = "adLGHcj_fmA";
    static final String YOUTUBE_PLAYLIST = "PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10";
    private static final String TAG = "YoutubeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_youtube);
        //ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_youtube);
        ConstraintLayout layout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_youtube , null);
        setContentView(layout);
       /* Button button1 = new Button(this);
        button1.setLayoutParams(new ConstraintLayout.LayoutParams(300,80));
        button1.setText("Button added");
        layout.addView(button1);*/
        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(playerView);
        playerView.initialize(GOOGLE_API_KEY , this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean WasRestored) {
        Log.d(TAG, "onInitializationSuccess: provider is "+provider.getClass().toString());
        Toast.makeText(this , "Initialized Youtube Player Successfully" , Toast.LENGTH_LONG).show();
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        if (!WasRestored)
        {
            youTubePlayer.cueVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        final int REQUEST_CODE =  1;
        if (youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this , REQUEST_CODE).show();
        }else {
            String errorMessage  =String.format("There was an error initializing the youtubePlayer (%1$s)" , youTubeInitializationResult.toString());
            Toast.makeText(this , errorMessage , Toast.LENGTH_LONG).show();
        }
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
            Toast.makeText(YoutubeActivity.this , "Good , video is playing ok" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onPaused() {
            Toast.makeText(YoutubeActivity.this , "Good , video is paused ok" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onStopped() {
            Toast.makeText(YoutubeActivity.this , "Video Stopped" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
            Toast.makeText(YoutubeActivity.this , "Click Ad now , make the video creator rich" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(YoutubeActivity.this , "Video has started" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(YoutubeActivity.this , "Congratulations , you completed another video" , Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}