package com.company;


import com.sun.source.tree.WhileLoopTree;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException {

        startGame(defouldF());
    }

    public static void startGame(BufferedImage foto ) throws IOException {

        //Определяем ширину и высоту эрана
        GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        int monitorWidth = gd[0].getDisplayMode().getWidth();
        int monitorHeight = gd[0].getDisplayMode().getHeight();

        BufferedImage nfoto = foto;
        while(((foto.getWidth(null) > 1778 || foto.getHeight(null) > 1000)) ){
           nfoto = new BufferedImage(foto.getWidth(null)/2, foto.getHeight(null)/2, foto.getType());
            Graphics2D g = nfoto.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(foto, 0, 0, foto.getWidth() / 2, foto.getHeight() / 2, 0, 0, foto.getWidth(), foto.getHeight() , null);
            g.dispose();
            foto = nfoto;
        }


        new Game(foto,monitorWidth,monitorHeight);

    }

    public static BufferedImage defouldF() throws IOException {

        ArrayList<String> massivImen = new ArrayList<>();
        massivImen.add("1.jpg");
        massivImen.add("2.jpg");
        massivImen.add("3.png");
        massivImen.add("4.jpg");
        massivImen.add("5.jpg");

        int nomberF = (int) (Math.random() * (massivImen.size()));
        if(nomberF == 5) nomberF = 4;
        BufferedImage foto = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream(massivImen.get(nomberF))));

        return foto;
    }



}
