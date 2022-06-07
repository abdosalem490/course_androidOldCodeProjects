package com.abdosalm.flappybird.game;

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
	Texture[] birds = new Texture[2];
	int flapState = 0;
	float birdY;
	float velocity = 0;
	int gameState = 0;
	float gravity = 2;
	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numOfTubes = 4;
	float[] tubeX = new float[numOfTubes];
	float[] tubeOffset = new float[numOfTubes];
	float distanceBetweenTubes;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	Rectangle[] topTubeRectangle;
	Rectangle[] bottomTubeRectangle;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	Texture gameOver;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight()/2f-birds[flapState].getHeight()/2f;
		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight()/2f-gap/2-100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth()* 3/4f;
		topTubeRectangle = new Rectangle[numOfTubes];
		bottomTubeRectangle = new Rectangle[numOfTubes];
		for (int i = 0 ; i < numOfTubes ; i++){
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - gap - 200);
			tubeX[i] = Gdx.graphics.getWidth()/2f-topTube.getWidth()/2f +Gdx.graphics.getWidth() + i * distanceBetweenTubes;
			topTubeRectangle[i] = new Rectangle();
			bottomTubeRectangle[i] = new Rectangle();
		}
		birdCircle = new Circle();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);
		gameOver = new Texture("gameover.png");
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (gameState == 1) {
			if (tubeX[scoringTube] < Gdx.graphics.getWidth()/2f){
				score++;
				if (scoringTube < numOfTubes - 1){
					scoringTube++;
				}else{
					scoringTube = 0;
				}
			}
			if (Gdx.input.justTouched()){
				velocity = -20;
			}
			for (int i = 0 ; i < numOfTubes ; i++) {
				if (tubeX[i] < -topTube.getWidth()){
					tubeX[i] += numOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - gap - 200);
				}else{
					tubeX[i] -= tubeVelocity;

				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2f + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2f - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);
				topTubeRectangle[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2f + gap / 2 + tubeOffset[i],topTube.getWidth(),topTube.getHeight());
				bottomTubeRectangle[i] = new Rectangle(tubeX[i],Gdx.graphics.getHeight() / 2f - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),bottomTube.getHeight());

			}
			if (birdY > 0){
				velocity += gravity;
				birdY -= velocity;
			}else{
				gameState = 2;
			}
		}else if (gameState == 0){
			if (Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if (gameState == 2){
			batch.draw(gameOver,Gdx.graphics.getWidth()/2f-gameOver.getWidth()/2f,Gdx.graphics.getHeight()/2f-gameOver.getHeight()/2f);
			if(Gdx.input.justTouched()){
				gameState = 1;
				birdY = Gdx.graphics.getHeight()/2f-birds[flapState].getHeight()/2f;
				for (int i = 0 ; i < numOfTubes ; i++){
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f)*(Gdx.graphics.getHeight() - gap - 200);
					tubeX[i] = Gdx.graphics.getWidth()/2f-topTube.getWidth()/2f +Gdx.graphics.getWidth() + i * distanceBetweenTubes;
					topTubeRectangle[i] = new Rectangle();
					bottomTubeRectangle[i] = new Rectangle();
				}
				score = 0;
				scoringTube = 0;
				velocity = 0;
				

			}
		}
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}

		batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2f - birds[flapState].getWidth() / 2f, birdY);
		font.draw(batch,String.valueOf(score),100,200);
		birdCircle.set(Gdx.graphics.getWidth()/2f,birdY+birds[flapState].getHeight()/2f,birds[flapState].getWidth()/2f);


		for (int i = 0; i < numOfTubes; i++) {

			if (Intersector.overlaps(birdCircle,topTubeRectangle[i]) || Intersector.overlaps(birdCircle,bottomTubeRectangle[i])){
				gameState = 2;
			}
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
