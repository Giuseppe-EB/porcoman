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
import com.mygdx.game.sprite.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class MyGdxGame extends ApplicationAdapter {

	private static Logger log= Logger.getLogger("log");

	SpriteBatch batch;
	Texture img;
	Texture porcodio;

	private boolean start=false;
	public static int height = 440;
	public static int width = 680;
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
		level = Level.getInstance();
		this.cam = new OrthographicCamera();
		this.input = new MouseInput(cam);
		try {
			sprites.add(new Player());
			sprites.add(new Nemico());
		} catch (IOException e) {
			e.printStackTrace();
		}

		cam.setToOrtho(false, this.width, this.height);
		this.view = new StretchViewport(this.width, this.height);
		this.stage = new Stage(view);
		batch = new SpriteBatch();
		img = new Texture("sfondo.png");
		porcodio = new Texture("stupid.png");
	}

	public void restart(){

		this.create();

	}
	@Override
	public void render () {
		stage.getCamera().update();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(stage.getCamera().combined);
		batch.begin();
		batch.draw(img, 0, 0);
		if(start) {
			Player player = null;
			if(sprites.get(0).getClass()==Player.class)
				player = (Player) sprites.get(0);

			level.draw(batch);




			if (player!=null&&player.isAi_hit()||Gdx.input.isKeyPressed((Input.Keys.Z)) && player.isCan_hit()) {
				sprites.add(new Bomb(player.getX(), player.getY(), player.getBombRange()));
				level.add_bomb(player.getX() / 40, player.getY() / 40);

			}
			//batch.draw(player.getTexture(), player.getX(), player.getY());
			ArrayList<Sprite> dead_sprite = new ArrayList<>();
			for (Sprite sprite : sprites) {
				if (sprite.isAlive()) {

					try {
						sprite.action();
					} catch (Exception e) {
						e.printStackTrace();
					}

					//batch.draw(sprite.getTexture(), sprite.getX(), sprite.getY());
					sprite.update(level);
					sprite.update_hitbox();
					sprite.draw(batch);
					if(sprite.getClass() == Player.class) {
						sprite.collision(sprites.get(1).getHitbox());
						for(Sprite sprite2 : sprites)
							if(sprite2.getClass() == Bomb.class && sprite2.collision(sprite.getHitbox())||
								sprite2.getClass() == Nemico.class && sprite.collision(sprite2.getHitbox())){
								dead_sprite.add(sprite);
							}

					}
					else if(sprite.getClass()==Nemico.class){
						sprite.update(sprites.get(0).getX(), sprites.get(0).getY());
						for(Sprite sprite2 : sprites)
							if(sprite2.getClass()==Bomb.class&&sprite2.collision(sprite.getHitbox())) {
								sprite.setAlive(false);
								dead_sprite.add(sprite);
								break;
							}
							else if(sprite2.getClass()==Player.class)
								sprite2.update(sprite.getX(), sprite.getY());

					}

				}

			}
			for (PowerUp powerUp : level.getPowerUps()) {
				powerUp.draw(batch);
				if(player.collision(powerUp.getHitbox())){
					player.dropPowerUp(powerUp);
					log.info("powerUpDropped");
					level.setDoorLocked(player.isDoorLocked());
					level.setBombRange(player.getBombRange());
					sprites.set(0, player);
					dead_sprite.add(powerUp);
				}
			}
			if(!dead_sprite.isEmpty())
				for(Sprite sprite : dead_sprite)
					if(!sprites.remove(sprite))
						level.getPowerUps().remove(sprite);



		}
		if(!start && (Gdx.input.isTouched()))
			start=true;


		batch.end();
		if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			this.restart();
		}
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
