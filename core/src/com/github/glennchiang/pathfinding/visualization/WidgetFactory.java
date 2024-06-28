package com.github.glennchiang.pathfinding.visualization;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class WidgetFactory {
    private static WidgetFactory instance;
    Skin skin;

    private WidgetFactory() {
        skin = new Skin();

        setDefaultTexture();
        setDefaultFont();
    }

    public static WidgetFactory getInstance() {
        if (instance == null) {
            instance = new WidgetFactory();
        }
        return instance;
    }

    private void setDefaultTexture() {
        // Create and store a white texture
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);;
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
    }
    
    public Label createLabel(String text) {
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = useFont();
        skin.add("default", labelStyle);
        return new Label(text, skin);
    }
    
    public SelectBox<String> createSelectBox(Array<String> items) {
        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = useFont();

        List.ListStyle listStyle = new List.ListStyle();
        listStyle.background = useTexture(Color.BLACK);
        listStyle.selection = useTexture(Color.BLUE);
        listStyle.font = useFont();
        style.listStyle = listStyle;

        style.background = useTexture(Color.BLACK);
        style.scrollStyle = new ScrollPane.ScrollPaneStyle();

        SelectBox<String> selectBox = new SelectBox<>(style);
        selectBox.setItems(items);
        return selectBox;
    }

    public TextButton createRunButton() {
        TextButton.TextButtonStyle runStyle = new TextButton.TextButtonStyle();
        runStyle.up = useTexture(Color.valueOf("#2ECC71"));
        runStyle.down = useTexture(Color.valueOf("#28B463"));
        runStyle.font = useFont();
        skin.add("run", runStyle);

        TextButton.TextButtonStyle pauseStyle = new TextButton.TextButtonStyle();
        pauseStyle.up = useTexture(Color.valueOf("#FDD835"));
        pauseStyle.down = useTexture(Color.valueOf("#FBC02D"));
        pauseStyle.font = useFont();
        skin.add("pause", pauseStyle);

        return new TextButton("Run", skin, "run");
    }

    public TextButton createResetButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = useTexture(Color.RED);
        buttonStyle.down = useTexture(Color.valueOf("#E53935"));
        buttonStyle.font = useFont();
        return new TextButton("Reset", buttonStyle);
    }

    private void setDefaultFont() {
        // Store default font
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);
        skin.add("default", font);
    }
    
    private BitmapFont useFont() {
        return skin.getFont("default");
    }

    private Drawable useTexture(Color color) {
        return skin.newDrawable("white", color);
    }

    public void setButtonStyle(TextButton button, String styleName) {
        button.setStyle(button.getSkin().get(styleName, TextButton.TextButtonStyle.class));
    }
}
