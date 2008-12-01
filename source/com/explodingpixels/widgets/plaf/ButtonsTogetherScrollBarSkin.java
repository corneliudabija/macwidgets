package com.explodingpixels.widgets.plaf;

import com.explodingpixels.painter.Painter;
import com.explodingpixels.swingx.EPPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * A {@link ScrollBarSkin} with the buttons placed at the bottom or right of the scroll bar.
 */
public class ButtonsTogetherScrollBarSkin implements ScrollBarSkin {

    private JComponent fCap;

    private JComponent fThumbContainer = new JPanel();

    private EPPanel fThumb = new EPPanel();

    private EPPanel fTrack = new EPPanel();

    private AbstractButton fDecrementButton;

    private AbstractButton fIncrementButton;

    private Dimension fMinimumThumbSize;

    private int fScrollBarCapRecess;

    private int fDecrementButtonTrackRecess;

    private final Dimension fPreferredSize;

    private static final Rectangle EMPTY_BOUNDS = new Rectangle(0, 0, 0, 0);

    /**
     * Creates a {@code ButtonsTogetherScrollBarSkin} using the given parameters.
     *
     * @param scrollBarCap          the component to draw adjacent to the scroll tracks minimum value side.
     * @param decrementButton       the button to cause a decrement in the scroll bar to occur.
     * @param incrementButton       the button to cause a increment in the scroll bar to occur.
     * @param trackPainter          the {@link Painter} to use to paint the track.
     * @param scrollThumbPainter    the {@link Painter} to use to paint the scroll thumb.
     * @param scrollBarCapRecess    the number of pixels to allow the scrollbar to "recess" into the
     *                              scroll bar cap. this is useful when using scroll bars with rounded ends.
     * @param decrementButtonRecess the number of pixels to allow the scrollbar to "recess" into the
     *                              decrement button. this is useful when using scroll bars with rounded ends.
     * @param minimumThumbSize      the minimum size that the scroll thumb can be.
     * @param preferredSize         the preferred size of this skin.
     */
    public ButtonsTogetherScrollBarSkin(
            JComponent scrollBarCap, AbstractButton decrementButton, AbstractButton incrementButton,
            Painter<Component> trackPainter, Painter<Component> scrollThumbPainter,
            int scrollBarCapRecess, int decrementButtonRecess, Dimension minimumThumbSize,
            Dimension preferredSize) {
        fCap = scrollBarCap;
        fDecrementButton = decrementButton;
        fIncrementButton = incrementButton;
        fTrack.setBackgroundPainter(trackPainter);
        fThumb.setBackgroundPainter(scrollThumbPainter);
        fScrollBarCapRecess = scrollBarCapRecess;
        fDecrementButtonTrackRecess = decrementButtonRecess;
        fMinimumThumbSize = minimumThumbSize;
        fPreferredSize = preferredSize;

        fThumbContainer.setLayout(null);
        fThumbContainer.setOpaque(false);

        fThumb.setOpaque(false);

//        fThumbContainer.setBorder(BorderFactory.createLineBorder(Color.RED));
//        fTrack.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    // ScrollBarSkin implementation. //////////////////////////////////////////////////////////////

    public Dimension getMinimumThumbSize() {
        return fMinimumThumbSize;
    }

    public Dimension getPreferredSize() {
        return fPreferredSize;
    }

    public Rectangle getScrollThumbBounds() {
        return fThumb.getBounds();
    }

    public Rectangle getTrackBounds() {
        return fThumbContainer.getBounds();
    }

    public void installComponents(JScrollBar scrollBar) {
        // add the components to the scrollbar. order matters here - components added first, are
        // drawn last (on top).
        scrollBar.add(fThumbContainer);
        scrollBar.add(fCap);
        scrollBar.add(fIncrementButton);
        scrollBar.add(fDecrementButton);
        scrollBar.add(fTrack);

        // add the actual scroller thumb (the component that will be painted) to the scroller thumb
        // container.
        fThumbContainer.add(fThumb);
    }

    public void trackLayoutOnly(JScrollBar scrollBar, ScrollBarOrientation orientation) {
        fCap.setBounds(EMPTY_BOUNDS);
        fIncrementButton.setBounds(EMPTY_BOUNDS);
        fDecrementButton.setBounds(EMPTY_BOUNDS);
        fThumbContainer.setBounds(EMPTY_BOUNDS);

        Rectangle r = scrollBar.getBounds();
        fTrack.setBounds(0, 0, r.width, r.height);
    }

    public void fullLayout(JScrollBar scrollBar, ScrollBarOrientation orientation) {
        // 1) layout the scroll bar cap.
        int capLength = orientation.getLength(fCap.getPreferredSize());
        fCap.setBounds(orientation.createBounds(scrollBar, 0, capLength));

        // 2) layout the scrollbar buttons.
        int incrementButtonHeight = orientation.getLength(fIncrementButton.getPreferredSize());
        int decrementButtonHeight = orientation.getLength(fDecrementButton.getPreferredSize());
        int scrollBarLength = orientation.getLength(scrollBar.getSize());
        int incrementButtonPosition = scrollBarLength - incrementButtonHeight;
        int decrementButtonPosition = incrementButtonPosition - decrementButtonHeight;

        fIncrementButton.setBounds(
                orientation.createBounds(scrollBar, incrementButtonPosition, incrementButtonHeight));
        fDecrementButton.setBounds(
                orientation.createBounds(scrollBar, decrementButtonPosition, decrementButtonHeight));

        // 3) layout the track and the scroller thumb container.
        // start the track and the scroller bar container overlapping the top scroller cap, if a
        // top cap recess has been specified. this handles a top cap that isn't square and is
        // intended to receive part of the scroller thumb.
        int trackAndThumbPosition = capLength - fScrollBarCapRecess;
        // the height of the track and scroll bar container should be slightly greater than that of
        // the empty space between the top cap, and the decrement button, if recesses have been
        // specified. that is, the track and scroll bar container will overlap the top cap and
        // decrement buttons if a non-zero recess has been specified.
        int trackLength = decrementButtonPosition + fDecrementButtonTrackRecess - trackAndThumbPosition;
        Rectangle trackAndThumbBounds =
                orientation.createBounds(scrollBar, trackAndThumbPosition, trackLength);
        fTrack.setBounds(trackAndThumbBounds);
        fThumbContainer.setBounds(trackAndThumbBounds);
    }

    public void installMouseListenersOnButtons(MouseListener decrementMoustListener,
                                               MouseListener incrementMouseListener) {
        fDecrementButton.addMouseListener(decrementMoustListener);
        fIncrementButton.addMouseListener(incrementMouseListener);
    }

    public void setScrollThumbBounds(Rectangle bounds) {
        fThumb.setBounds(bounds);
    }

}
