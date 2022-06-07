package com.example.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity = 2;
	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubeOffSet;
	Random mRandom;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffSet = new float[numberOfTubes];
	float distanceBetweenTubes;
	Circle birdCircle;
	ShapeRenderer mShapeRenderer;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	Texture gameOver;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		topTube = new Texture("toptube.png");
		gameOver = new Texture("gameover.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffSet = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		mRandom = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		startGame();

		mShapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();


	}
	private void startGame()
	{
		birdY = Gdx.graphics.getHeight() /2 - birds[flapState].getHeight() /2;
		for (int i = 0 ; i < numberOfTubes ; i++ )
		{
			tubeOffSet[i] = (mRandom.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2 - topTube.getWidth()/2 + Gdx.graphics.getWidth() + i*distanceBetweenTubes;
			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();
		}
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (gameState == 1) {
			if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2)
			{
				score++;
				Gdx.app.log("h",String.valueOf(score));
				if (scoringTube < numberOfTubes - 1)
				{
					scoringTube++;
				}else {
					scoringTube = 0;
				}
			}
			if (Gdx.input.justTouched()) {
				velocity = -30;

			}
			for (int i = 0 ; i < numberOfTubes ; i++ ) {
				if (tubeX[i] < -topTube.getWidth())
				{
					tubeOffSet[i] = (mRandom.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
				}else {
					tubeX[i] = tubeX[i] - tubeVelocity;

				}
				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i]);
				topTubeRectangles[i] = new Rectangle(tubeX[i] , Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i] , topTube.getWidth() , topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i] , Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i], topTube.getWidth() , topTube.getHeight());

			}

			if (birdY > 0)
			{
				velocity += gravity;
				birdY -= velocity;
			}else {
				gameState = 2;
			}

		}else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}else if (gameState == 2)
		{
			batch.draw(gameOver , Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			if (Gdx.input.justTouched()) {
				gameState = 1;
				startGame();
				scoringTube = 0;
				score = 0;
				velocity = 0;
				
			}
		}

		if (flapState == 0) {
			flapState = 1;
		} else {

			flapState = 0;
		}

		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
		font.draw(batch , String.valueOf(score) , 100 , 200);
		batch.end();
		birdCircle.set(Gdx.graphics.getWidth()/2 , birdY + birds[flapState].getHeight() / 2 , birds[flapState].getWidth() / 2);

		//mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//mShapeRenderer.setColor(Color.RED);
		//mShapeRenderer.circle(birdCircle.x , birdCircle.y , birdCircle.radius);
		for (int i= 0 ; i < numberOfTubes ; i++)
		{
		//	mShapeRenderer.rect(tubeX[i] , Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffSet[i] , topTube.getWidth() , topTube.getHeight());
		//	mShapeRenderer.rect(tubeX[i] , Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffSet[i], topTube.getWidth() , topTube.getHeight());
			if (Intersector.overlaps(birdCircle , topTubeRectangles[i]) || Intersector.overlaps(birdCircle , bottomTubeRectangles[i]))
			{
				gameState = 2;
			}
		}
		//mShapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
