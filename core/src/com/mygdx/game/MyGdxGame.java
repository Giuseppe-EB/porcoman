package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.level.Level;
import com.mygdx.game.sprite.Bomb;
import com.mygdx.game.sprite.Player;
import com.mygdx.game.sprite.Sprite;
import org.graalvm.compiler.phases.common.NodeCounterPhase;

import java.io.IOException;
import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture porcodio;
	public static int height = 640;
	public static int width = 1280;
	private OrthographicCamera cam;
	private StretchViewport view;
	private Stage stage;
	private Level level;
	//private Player player;
	private ArrayList<Sprite> sprites;
	public static MouseInput input;
	@Override
	public void create () {
		sprites = new ArrayList<>();
		try {
			level = new Level();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.cam = new OrthographicCamera();
		this.input = new MouseInput(cam);
		sprites.add(new Player());
		cam.setToOrtho(false, this.width, this.height);
		this.view = new StretchViewport(this.width, this.height);
		this.stage = new Stage(view);
		batch = new SpriteBatch();
		img = new Texture("the-witcher-3-caccia-selvaggia-geralt-e-ciri-sfondo-1920x1080-21727_48.jpg");
		porcodio = new Texture("stupid.png");
	}

	@Override
	public void render () {
		stage.getCamera().update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(stage.getCamera().combined);
		batch.begin();
		batch.draw(img, 0, 0);
		/*if(Gdx.input.isKeyPressed(Input.Keys.A)){
				player.setX(player.getX()-10);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
				player.setX(player.getX()+10);
		}
		if(Gdx.input.isKeyPressed((Input.Keys.W)))
			player.setY(player.getY()+10);
		if(Gdx.input.isKeyPressed((Input.Keys.S)))
			player.setY(player.getY()-10);

		 */

		input.convertInput();
		Player player = (Player)sprites.get(0);


			level.draw(batch);



			if (Gdx.input.isKeyPressed((Input.Keys.Z))&& player.isCan_hit()) {
			sprites.add(new Bomb(player.getX(), player.getY()));
		}
		//batch.draw(player.getTexture(), player.getX(), player.getY());
			for ( Sprite sprite : sprites ){
				if(sprite.isAlive()) {
					sprite.action();
					batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY());
				}

			}
		//System.out.println("porcodio");
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
