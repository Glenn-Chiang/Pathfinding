package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class WidgetFactory {
    private static WidgetFactory instance;
    Skin skin;

    private WidgetFactory() {
        skin = new Skin();

        // Create and store a white texture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);;
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store default font
        skin.add("default", new BitmapFont());
    }

    public static WidgetFactory getInstance() {
        if (instance == null) {
            instance = new WidgetFactory();
        }
        return instance;
    }

    public Button createStartButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = useColoredTexture(Color.DARK_GRAY);
        buttonStyle.down = useColoredTexture(Color.DARK_GRAY);
        buttonStyle.checked = useColoredTexture(Color.BLUE);
        buttonStyle.over = useColoredTexture(Color.LIGHT_GRAY);
        buttonStyle.font = useFont();
        return new TextButton("Start", buttonStyle);
    }

    public Button createResetButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = useColoredTexture(Color.DARK_GRAY);
        buttonStyle.down = useColoredTexture(Color.DARK_GRAY);
        buttonStyle.checked = useColoredTexture(Color.BLUE);
        buttonStyle.over = useColoredTexture(Color.LIGHT_GRAY);
        buttonStyle.font = useFont();
        return new TextButton("Stop", buttonStyle);
    }

    private BitmapFont useFont() {
        return skin.getFont("default");
    }

    private Drawable useColoredTexture(Color color) {
        return skin.newDrawable("white", color);
    }
}
