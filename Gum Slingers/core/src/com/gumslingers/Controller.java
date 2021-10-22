package com.gumslingers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class Controller implements InputProcessor {
    public boolean left;
    public boolean right;
    public boolean jump;
    public boolean shoot;
    public boolean hook;
    public float xPosBullet;
    public float yPosBullet;
    public float xPosHook;
    public float yPosHook;

    @Override
    public boolean keyDown(int keycode) {
        boolean processed = false;

        switch (keycode) {
            case Input.Keys.A:
                left = true;
                processed = true;
                break;

            case Input.Keys.D:
                right = true;
                processed = true;
                break; 
            
            case Input.Keys.SPACE:
                jump = true;
                processed = true;
                break;

            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                processed = true;
                break;
        }

        return processed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean processed = false;

        switch (keycode) {
            case Input.Keys.A:
                left = false;
                processed = true;
                break;

            case Input.Keys.D:
                right = false;
                processed = true;
                break;

            case Input.Keys.SPACE:
                jump = false;
                processed = true;
                break;
        }

        return processed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        boolean processed = false;

        switch (button) {
            case Input.Buttons.LEFT:
                xPosBullet = screenX;
                yPosBullet = screenY;
                shoot = true;
                processed = true;
                break;

            case Input.Buttons.RIGHT:
                xPosHook = screenX;
                yPosHook = screenY;
                hook = true;
                processed = true;
                break;
        }

        return processed;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        boolean processed = false;

        switch (button) {
            case Input.Buttons.LEFT:
                shoot = false;
                processed = true;
                break;
                
            case Input.Buttons.RIGHT:
                hook = false;
                processed = true;
                break;
        }

        return processed;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    
}