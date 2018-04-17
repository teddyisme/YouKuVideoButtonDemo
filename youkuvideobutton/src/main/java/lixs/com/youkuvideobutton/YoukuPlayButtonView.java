package lixs.com.youkuvideobutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author XinSheng
 * @description
 * @date 2018/4/16
 */
public class YoukuPlayButtonView extends View {
    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;

    private int mWidth;
    private float borderWidth;
    private float borderHeight;

    private float percent = 0.0f;
    private float circleSweep;

    private float leftRight;
    private float rightLeft;

    private Boolean isplay = true;

    private Boolean isRunning = false;

    public YoukuPlayButtonView(Context context) {
        super(context);
        init(context);
    }

    private Context mContext;

    private void init(Context context) {
        mContext = context;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(context, R.color.dark_blue));
        mPaint.setStyle(Paint.Style.FILL);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(ContextCompat.getColor(context, R.color.light_blue));
        mPaint1.setStyle(Paint.Style.STROKE);

        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(0);
        mPaint2.setColor(ContextCompat.getColor(context, R.color.red));
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
    }

    public YoukuPlayButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YoukuPlayButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public YoukuPlayButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        borderWidth = (float) (mWidth * 0.16);
        borderHeight = (float) (Math.cos(Math.PI / 4) * 0.8 * mWidth);

        mPaint1.setStrokeWidth(borderWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isplay) {
            leftRight = (float) ((0.5 - 0.4 * Math.cos(Math.PI / 4)) * mWidth) + borderWidth / 2 + borderHeight * percent;
            rightLeft = (float) (0.8 * mWidth * 0.5 * Math.cos(Math.PI / 4) + mWidth / 2 - borderWidth / 2) - borderHeight * percent;
            circleSweep = 180f * (1 - percent);
        } else {
            leftRight = (float) ((0.5 - 0.4 * Math.cos(Math.PI / 4)) * mWidth) + borderWidth / 2 + borderHeight - borderHeight * percent;
            rightLeft = (float) (0.8 * mWidth * 0.5 * Math.cos(Math.PI / 4) + mWidth / 2 - borderWidth / 2) - borderHeight + borderHeight * percent;
            circleSweep = 180f * percent;
        }
        canvas.save();

        if (isplay) {
            canvas.rotate(-90 * percent, mWidth / 2, mWidth / 2);
        } else {
            canvas.rotate(90 * (1 + percent), mWidth / 2, mWidth / 2);
        }

        canvas.drawArc((float) 0.1 * mWidth,
                (float) 0.1 * mWidth,
                (float) 0.9 * mWidth,
                (float) (0.9 * mWidth),
                135f, circleSweep, false, mPaint1);

        canvas.drawArc((float) 0.1 * mWidth,
                (float) 0.1 * mWidth,
                (float) 0.9 * mWidth,
                (float) (0.9 * mWidth),
                -45f, circleSweep, false, mPaint1);
        canvas.drawRoundRect((float) ((0.5 - 0.4 * Math.cos(Math.PI / 4)) * mWidth - borderWidth / 2),
                (float) ((0.5 - 0.4 * Math.cos(Math.PI / 4)) * mWidth - borderWidth / 2) + borderHeight,
                leftRight,
                ((float) ((0.5 - 0.4 * Math.cos(Math.PI / 4)) * mWidth) + borderWidth / 2) + borderHeight,
                borderWidth / 2, borderWidth / 2,
                mPaint);

        canvas.drawRoundRect(
                rightLeft,
                (float) (0.8 * mWidth * 0.5 * Math.cos(Math.PI / 4) + mWidth / 2 - borderWidth / 2) - borderHeight,
                ((float) ((0.8 * mWidth * 0.5 * Math.cos(Math.PI / 4) + mWidth / 2) + borderWidth / 2)),
                (float) (0.8 * mWidth * 0.5 * Math.cos(Math.PI / 4) + mWidth / 2 + borderWidth / 2) - borderHeight,
                borderWidth / 2, borderWidth / 2,
                mPaint);
        canvas.restore();

        canvas.save();

        if (isplay) {
            if (percent < 0.7) {
                canvas.rotate(90 * percent, (float) (mWidth / 2), (float) mWidth / 2);
                canvas.save();
                canvas.rotate(30, (float) (mWidth - borderWidth * 2), (float) mWidth / 2);
                canvas.drawRoundRect((float) (mWidth / 2 - borderWidth * 1.5),
                        mWidth / 2 - borderWidth / 2,
                        (float) (mWidth - borderWidth * 1.5),
                        mWidth / 2 + borderWidth / 2,
                        borderWidth / 2, borderWidth / 2,
                        mPaint2);
                canvas.restore();

                canvas.save();
                canvas.rotate(-30, (mWidth - borderWidth * 2), (float) mWidth / 2);
                canvas.drawRoundRect((float) (mWidth / 2 - borderWidth * 1.5),
                        mWidth / 2 - borderWidth / 2,
                        (float) (mWidth - borderWidth * 1.5),
                        mWidth / 2 + borderWidth / 2,
                        borderWidth / 2, borderWidth / 2,
                        mPaint2);
                canvas.restore();
            }
        } else {
            if (percent > 0.7) {
                canvas.rotate(-90 * percent, (float) (mWidth / 2), (float) mWidth / 2);
                canvas.save();
                canvas.rotate(30, mWidth / 2, (float) (mWidth - borderWidth * 2));
                canvas.drawRoundRect(mWidth / 2 - borderWidth / 2,
                        (float) (mWidth / 2 - borderWidth * 1.5),
                        mWidth / 2 + borderWidth / 2,
                        (float) (mWidth - borderWidth * 1.5),
                        borderWidth / 2, borderWidth / 2,
                        mPaint2);
                canvas.restore();

                canvas.save();
                canvas.rotate(-30, mWidth / 2, (float) (mWidth - borderWidth * 2));
                canvas.drawRoundRect(mWidth / 2 - borderWidth / 2,
                        (float) (mWidth / 2 - borderWidth * 1.5),
                        mWidth / 2 + borderWidth / 2,
                        (float) (mWidth - borderWidth * 1.5),
                        borderWidth / 2, borderWidth / 2,
                        mPaint2);
                canvas.restore();
            }

        }

        canvas.restore();

        isRunning = percent < 1 && percent > 0;
    }

    public void startAnimation() {
        if (isRunning) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                percent = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isplay = !isplay;
                    }
                }, 100);

            }
        });
        animator.start();
    }
}
