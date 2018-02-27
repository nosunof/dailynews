package com.dailynews.android;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dailynews.android.view.LauncherView;

import java.util.Random;

public class LauncherActivity extends AppCompatActivity {

    private RelativeLayout re_layout;
    private ImageView launcherImage;
    private float mHeight;
    private LauncherView launcherView;
    private int[] images = {R.drawable.launch,
                            R.drawable.start0,
                            R.drawable.start1,
                            R.drawable.start2,
                            R.drawable.start3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        re_layout = (RelativeLayout)findViewById(R.id.re_layout);
        launcherView = (LauncherView)findViewById(R.id.zhiview);
        re_layout.post(new Runnable() {
            @Override
            public void run() {
                //获取位移布局的高度
                mHeight = re_layout.getHeight();
                //开始动画
                startAnimation();
                //延时加载图片
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initImage();
                    }

                },2000);
            }
        });
    }

    //初始化启动图片
    private void initImage(){
        launcherImage = (ImageView)findViewById(R.id.image_view);
        launcherImage.setImageResource(images[new Random().nextInt(images.length)]);
        //设置缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.4f,
                1.1f,
                1.4f,
                1.1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnimation.setDuration(1500);
        ///动画播放完成后保持形状
        scaleAnimation.setFillAfter(true);
        //设置动画监听器，缩放动画完成就finish当前活动
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                //设置活动切换的过渡动画
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动缩放动画
        launcherImage.setAnimation(scaleAnimation);
    }

    private void startAnimation(){
        //位移动画，从底部滑出，Y方向移动
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(re_layout,
                "translationY",mHeight,0f);
        //设置时长
        translationAnimator.setDuration(1000);
        //透明度渐变动画
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(re_layout,
                "alpha",0f,1f);
        //设置时长
        alphaAnimator.setDuration(2500);
        //添加监听器，为位移结束后，画圆弧开始
        translationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                launcherView.starAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        AnimatorSet set = new AnimatorSet();
        //两个动画一起执行
        set.play(translationAnimator).with(alphaAnimator);
        set.start();
    }
}
