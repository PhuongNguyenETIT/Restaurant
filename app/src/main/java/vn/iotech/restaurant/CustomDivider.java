package vn.iotech.restaurant;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class CustomDivider extends RecyclerView.ItemDecoration {
    private Drawable drawable;
    private int PaddingLeft;
    private int PaddingRight;

    public CustomDivider(Context context, int padding, int paddingRight) {
        drawable = ContextCompat.getDrawable(context,R.drawable.custom_divider);
        PaddingLeft = padding;
        PaddingRight = paddingRight;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft()+ PaddingLeft;
        int right = parent.getWidth() - parent.getPaddingRight() - PaddingRight;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();

            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }
}
