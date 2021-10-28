package com.mironov.flowlayout;
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup


class FlowLayout : ViewGroup {
    private var lineHeightSpace = 0

    class LayoutParams
    /**
     * @param horizontal_spacing Pixels between items, horizontally
     * @param vertical_spacing   Pixels between items, vertically
     */(var horizontal_spacing: Int, var vertical_spacing: Int) :
        ViewGroup.LayoutParams(0, 0) {
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)
        val width = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        var height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count = childCount
        var lineHeightSpace = 5
        var xpos = paddingLeft
        var ypos = paddingTop
        val childHeightMeasureSpec: Int = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST)
            } else {
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            }
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val lp = child.layoutParams as LayoutParams
                child.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    childHeightMeasureSpec
                )
                val childw = child.measuredWidth
                lineHeightSpace =
                    Math.max(lineHeightSpace, child.measuredHeight + lp.vertical_spacing)
                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += lineHeightSpace
                }
                xpos += childw + lp.horizontal_spacing
            }
        }
        this.lineHeightSpace = lineHeightSpace
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + lineHeightSpace
        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + lineHeightSpace < height) {
                height = ypos + lineHeightSpace
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(1, 1) // default of 1px spacing
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is LayoutParams
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xpos = paddingLeft
        var ypos = paddingTop
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != GONE) {
                val childw = child.measuredWidth
                val childh = child.measuredHeight
                val lp = child.layoutParams as LayoutParams
                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += lineHeightSpace
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh)
                xpos += childw + lp.horizontal_spacing
            }
        }
    }
}