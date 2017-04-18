package com.kwala.app.helpers.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.kwala.app.R;

/**
 * A framelayout which is used in conjunction with a viewpager. This view allows touches to be
 * passed to the viewpager which are normally outside of the viewpager's bounds. Also has the
 * clip children false set to true so that views on the edge of the viewpager can be seen.
 * ("peeking views")
 * <p/>
 * Structured after https://gist.github.com/devunwired/8cbe094bb7a783e37ad1
 */
public class PagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = PagerContainer.class.getSimpleName();

    private static final int PAGER_ID_NONE = -1;

    private int viewPagerId = PAGER_ID_NONE;
    private ViewPager viewPager;

    private boolean needsRedraw = false;

    public PagerContainer(Context context) {
        super(context);
        initialize(null);
    }

    public PagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public PagerContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PagerContainer);

            viewPagerId = typedArray.getResourceId(R.styleable.PagerContainer_viewPager, PAGER_ID_NONE);

            typedArray.recycle();
        }

        /**
         * Necessary to show views outside of the viewpager's frame.
         */
        setClipChildren(false);

        /**
         * Child clipping doesn't work with hardware acceleration in Android 3.x/4.x You need to
         * set this value here if using hardware acceleration in an application targeted at these
         * releases.
         */
        if (Build.VERSION.SDK_INT < 19) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    /**
     * Make sure that the child of this container is a viewpager if not, throw an exception.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (viewPagerId != PAGER_ID_NONE) {
            viewPager = (ViewPager) findViewById(viewPagerId);
        }

        //If viewPager id wasn't provided in XML or viewPager wasn't found, check the first child
        if (viewPager == null) {
            try {
                viewPager = (ViewPager) getChildAt(0);
            } catch (Exception e) {
                throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
            }
        }

        viewPager.addOnPageChangeListener(this);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private Point center = new Point();
    private Point initialTouch = new Point();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        center.x = w / 2;
        center.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        /**
         * Capture any touches outside of the viewpager but within our container.
         */
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialTouch.x = (int) motionEvent.getX();
                initialTouch.y = (int) motionEvent.getY();
            default:
                motionEvent.offsetLocation(viewPager.getWidth()/2 - center.x, 0);
                break;
        }

        return viewPager.dispatchTouchEvent(motionEvent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /**
         * Force the container to redraw when scrolling.
         * Without this, the outer pages render initially but then stay static.
         */
        if (needsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        needsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
