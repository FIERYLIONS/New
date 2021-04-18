package com.company;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Game extends JFrame {

    final ArrayList<JButton> buttens = new ArrayList();
    final HashMap<String, String> gameOver = new HashMap<>();

    public Game(BufferedImage foto, int monitorWidth, int monitorHeight) {

        HashMap<String,BufferedImage> MassivKartinok = new HashMap<>();

        int setka = 3;

        JFrame form = new JFrame();
        form.setTitle("Кручу-верчу как хочу...");
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setAlwaysOnTop(false);
        //Первый монитор
        form.setLocation((monitorWidth - foto.getWidth(null)) / 2, (monitorHeight - foto.getHeight(null)) / 2); //Около середины
        //form.setLocation(1400, 200);
//        form.setLocation(1100, 10);
        //Второй монитор
        //form.setLocation(3200, 200);
        form.setResizable(false);
        form.setSize(foto.getWidth(null), foto.getHeight(null));
        form.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new

                GridLayout(setka, setka));

        form.add(BorderLayout.CENTER, panel);

        JButton buttonNewFoto = new JButton();
        buttonNewFoto.setName("buttonF");
        buttonNewFoto.setText("Выбрать фото");
        buttonNewFoto.addMouseListener(new
                                               MouseAdapter() {
                                                   public void mouseClicked(MouseEvent me) {
                                                       FileDialog dialog = new FileDialog(form, "Выберите картинку", FileDialog.LOAD);
                                                       dialog.setVisible(true);

                                                       String image = dialog.getFile();

                                                       try {
                                                           BufferedImage fotoNew = ImageIO.read(new File(dialog.getDirectory() + image));
                                                           new Main().startGame(fotoNew);
                                                           form.dispose();
                                                       } catch (IOException e) {
                                                           e.printStackTrace();
                                                       }


                                                   }
                                               });

        JButton buttonNewGame = new JButton();
        buttonNewGame.setName("buttonNewGame");
        buttonNewGame.setText("Новая игра");
        buttonNewGame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                try {
                    Main nM = new Main();
                    nM.startGame(nM.defouldF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                form.dispose();
            }
        });

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new

                GridLayout(1, 2));

        panelButtons.add(buttonNewFoto);
        panelButtons.add(buttonNewGame);
        form.add(BorderLayout.SOUTH, panelButtons);

        int OnaChastW = foto.getWidth(null) / 3;
        int OnaChastH = foto.getHeight(null) / 3;

        int nColum = 0;
        int nRow = 0;

        for (int i = 0; i < 8; i++) {

            if (nColum == 3) {
                nColum = 0;
                nRow++;
            }
            BufferedImage nFoto = foto.getSubimage(nColum * OnaChastW, nRow * OnaChastH, OnaChastW, OnaChastH);
            MassivKartinok.put(Integer.toString(i),nFoto);
            nColum++;
        }

        for (int i = 1; i < 10; i++) {

            JButton button = new JButton();
            button.setName("button" + i);
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    // button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }

                public void mouseExited(MouseEvent e) {
                    // button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                }

                public void mouseClicked(MouseEvent me) {

//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                    JButton knopkaSkartinkoy = (JButton) me.getSource();
                    if (knopkaSkartinkoy.getText() == "") {
                        return;
                    }

                    JButton knopkaBesKartinki = null;
                    for (int i = 0; i < buttens.size(); i++) {
                        if (buttens.get(i).getText() == "") {
                            knopkaBesKartinki = buttens.get(i);
                        }
                    }

                    //Пустую кнопку делаем с картинкой
                    knopkaBesKartinki.setIcon(knopkaSkartinkoy.getIcon());
                    knopkaBesKartinki.setText(knopkaSkartinkoy.getText());

                    //Кнопку с картинкой делаем пустой
                    knopkaSkartinkoy.setIcon(null);
                    knopkaSkartinkoy.setText("");

                    //Проверяем на окончание игры
                    gameover(form);

                }
            });

            panel.add(button, i - 1);


            if (i == (setka * setka)) {
                button.setText("");
                button.setBorderPainted(false);
                //button.setContentAreaFilled(false);
                buttens.add(button);
            } else {
                buttens.add(button);
            }
        }

        //Заполняем последовательно что бы потом можно было сравнить
        for (int i = 0; i < 8; i++) {
            if (nColum == 3) {
                nColum = 0;
                nRow++;
            }
            BufferedImage pF = MassivKartinok.get(Integer.toString(i));
            buttens.get(i).setIcon(new ImageIcon(pF.getScaledInstance(pF.getWidth(),
                    pF.getHeight() - 22, Image.SCALE_SMOOTH)));
            buttens.get(i).setText(Integer.toString(i));

            //По этому массиву будем определять конец игры
            gameOver.put(buttens.get(i).getName(), Integer.toString(i));

            nColum++;
        }

        //Теперь все тоже самое только рандомно раскидываем изображения
        nColum = 0;
        nRow = 0;
        int vseiloP = MassivKartinok.size();
        for (int i = 0; i < 8; i++) {
            if (nColum == 3) {
                nColum = 0;
                nRow++;
            }

            Random random = new Random();
            BufferedImage pF = null;
            int nomberF = 0;
            while (pF == null) {
                nomberF = random.nextInt(vseiloP);
                pF = MassivKartinok.get(Integer.toString(nomberF));
            }
            buttens.get(i).setIcon(new ImageIcon(pF.getScaledInstance(pF.getWidth(),
                    pF.getHeight() - 22, Image.SCALE_SMOOTH)));
            buttens.get(i).setText(Integer.toString(nomberF));
            MassivKartinok.remove(Integer.toString(nomberF));

            nColum++;
        }
        MassivKartinok.clear();
    }

    private void gameover(JFrame form) {

        //Проверяем правильно ли собрали картинку.
        boolean end = true;
        for (int i = 0; i < gameOver.size(); i++) {
            if (Integer.parseInt(buttens.get(i).getText()) != Integer.parseInt(gameOver.get(buttens.get(i).getName()))) {
                end = false;
            }
        }
        if (end) {
            JOptionPane.showMessageDialog(form, "Game over!");
        }


    }


}


