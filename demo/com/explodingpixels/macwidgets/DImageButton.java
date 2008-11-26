package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.ImageButton;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class DImageButton {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Icon icon = new ImageIcon(
                        DImageButton.class.getResource("./icons/scroller.png"));
                Icon pressedIcon = new ImageIcon(
                        DImageButton.class.getResource("./icons/scroller_pressed.png"));
                Icon mask = new ImageIcon(
                        DImageButton.class.getResource("./icons/scroller_mask.png"));

                ImageButton button = new ImageButton(icon, mask);
                button.setPressedIcon(pressedIcon);

                JFrame frame = new JFrame();
                frame.add(button, BorderLayout.CENTER);
                frame.setSize(150, 100);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
