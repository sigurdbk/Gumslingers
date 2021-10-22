package com.gum.slingers.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gumslingers.GumSlingers;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Gum Slingers";
		// config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        // config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		// config.fullscreen = true;
		new LwjglApplication(new GumSlingers(), config);
	}
}
 