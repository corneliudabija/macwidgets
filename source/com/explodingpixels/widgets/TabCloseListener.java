/* Copyright 2009 The MathWorks, Inc. */

package com.explodingpixels.widgets;

import java.awt.Component;

public interface TabCloseListener {

    boolean tabAboutToBeClosed(int tabIndex);

    void tabClosed(String title, Component component);

}
