package com.explodingpixels.macwidgets;

import com.explodingpixels.widgets.PopupMenuCustomizerUsingStrings;
import com.explodingpixels.widgets.WindowUtils;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSourceList {

    public static void main(String[] args) {

        Icon blueGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSDotMac").getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        Icon greyGlobeIcon =
                new ImageIcon(Toolkit.getDefaultToolkit().getImage("NSImage://NSNetwork").getScaledInstance(16, 16, Image.SCALE_SMOOTH));

        SourceListCategory categoryOne = new SourceListCategory("Category 1");
        SourceListCategory categoryTwo = new SourceListCategory("Category 2");
        SourceListCategory categoryThree = new SourceListCategory("Category 3");

        SourceListItem itemA = new SourceListItem("SourceListItem A", blueGlobeIcon);
        final SourceListItem itemB = new SourceListItem("SourceListItem B", greyGlobeIcon);

        SourceListItem itemC = new SourceListItem("SourceListItem C", greyGlobeIcon);
        SourceListItem itemD = new SourceListItem("SourceListItem D", greyGlobeIcon);
        SourceListItem itemE = new SourceListItem("Really really really really really long SourceListItem", greyGlobeIcon);

        SourceListItem itemG = new SourceListItem("SourceListItem G", greyGlobeIcon);
        SourceListItem itemH = new SourceListItem("SourceListItem H", greyGlobeIcon);
        SourceListItem itemI = new SourceListItem("SourceListItem I", greyGlobeIcon);
        SourceListItem itemJ = new SourceListItem("SourceListItem J", greyGlobeIcon);
        SourceListItem itemK = new SourceListItem("SourceListItem K", greyGlobeIcon);
        SourceListItem itemL = new SourceListItem("SourceListItem L", greyGlobeIcon);

        itemA.setCounterValue(1);
        itemC.setCounterValue(385);
        itemE.setCounterValue(3);
        itemI.setCounterValue(17);

        final SourceListModel model = new SourceListModel();
        model.addCategory(categoryOne);
        model.addItemToCategory(itemA, categoryOne);
        model.addItemToCategory(itemB, categoryOne);

        model.addCategory(categoryTwo);
        model.addItemToCategory(itemC, categoryTwo);
//        model.addItemToCategory(itemD, categoryTwo);
        model.addItemToItem(itemD, itemC);
//        model.addItemToCategory(itemE, categoryTwo);
        model.addItemToItem(itemE, itemD);

        model.addCategory(categoryThree);
        model.addItemToCategory(itemG, categoryThree);
        model.addItemToCategory(itemH, categoryThree);
        model.addItemToCategory(itemI, categoryThree);
        model.addItemToCategory(itemJ, categoryThree);
        model.addItemToCategory(itemK, categoryThree);
        model.addItemToCategory(itemL, categoryThree);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                SourceList sourceList = new SourceList(model);
//        sourceList.setFocusable(false);

                sourceList.setSelectedItem(itemB);

                final JTextField textField = new JTextField("");
                JButton button = new JButton("Add");
                button.putClientProperty("JButton.buttonType", "textured");
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        model.addCategory(new SourceListCategory(textField.getText()));
                    }
                });

                SourceListControlBar controlBar = new SourceListControlBar();
                controlBar.createAndAddButton(MacIcons.PLUS, null);
                controlBar.createAndAddButton(MacIcons.MINUS, null);
                controlBar.createAndAddPopdownButton(MacIcons.GEAR,
                        new PopupMenuCustomizerUsingStrings(null, "Item One", "Item Two", "Item Three"));

                sourceList.installSourceListControlBar(controlBar);

                JTextArea textArea = new JTextArea();

                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                        sourceList.getComponent(), textArea);
                splitPane.setDividerLocation(200);
                splitPane.setContinuousLayout(true);
                splitPane.setDividerSize(1);
                ((BasicSplitPaneUI) splitPane.getUI()).getDivider().setBorder(
                        BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(0xa5a5a5)));
                splitPane.setBorder(BorderFactory.createEmptyBorder());

                controlBar.installDraggableWidgetOnSplitPane(splitPane);

                // definte the FormLayout columns and rows.
                FormLayout layout = new FormLayout(
                        "fill:p:grow, 0dlu, p", "p");
                // create the cell constraints to use in the layout.
                CellConstraints cc = new CellConstraints();
                // create the builder with our panel as the component to be filled.
                PanelBuilder builder = new PanelBuilder(layout);

//        builder.add(textField, cc.xy(1,1));
//        builder.add(button, cc.xy(3,1));

                JFrame frame = new JFrame();
                WindowUtils.createAndInstallRepaintWindowFocusListener(frame);
                frame.add(builder.getPanel(), BorderLayout.NORTH);
                frame.add(splitPane, BorderLayout.CENTER);
                frame.setSize(500, 350);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setVisible(true);


            }
        });
    }

}
