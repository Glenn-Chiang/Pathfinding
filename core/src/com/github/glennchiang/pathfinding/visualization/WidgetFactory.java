package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public TextButton createRunButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = useColoredTexture(Color.valueOf("#2ECC71"));
        buttonStyle.down = useColoredTexture(Color.valueOf("#28B463"));
        buttonStyle.checked = useColoredTexture(Color.GOLD);
        buttonStyle.font = useFont();
        return new TextButton("Run", buttonStyle);
    }

    public TextButton createResetButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = useColoredTexture(Color.RED);
        buttonStyle.down = useColoredTexture(Color.valueOf("#E53935"));
        buttonStyle.font = useFont();
        return new TextButton("Reset", buttonStyle);
    }

    private BitmapFont useFont() {
        return skin.getFont("default");
    }

    private Drawable useColoredTexture(Color color) {
        return skin.newDrawable("white", color);
    }
}
