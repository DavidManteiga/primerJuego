package com.example.dm2.primerjuego;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dm2 on 07/01/2016.
 */
public class VistaJuego extends SurfaceView {
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite2 sprite2;
    private Moneda moneda;
    private List<Moneda> monedas = new ArrayList<Moneda>();
    private Bitmap bmp;
    private Bitmap bmp2;


//private int x = 0;
//private int xSpeed = 6;

    public VistaJuego(Context context) {
        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.principal);
        sprite2 = new Sprite2(this,bmp);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createSprites();
                createMonedas();
                //
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        /*
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.moneda);
        moneda=new Moneda(this,bmp2);
        */





        //sprite2.setY(sprite2.getY()+sprite2.getHeight()/2);

//bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//bmp = BitmapFactory.decodeResource(getResources(), R.drawable.princesa);
//sprite = new Sprite(this,bmp);

    }
    private void createSprites() {
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));
        sprites.add(createSprite(R.drawable.personaje));


    }

    private void createMonedas() {

        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));
        monedas.add(createMoneda(R.drawable.moneda));


    }

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this,bmp);
    }
    private Moneda createMoneda(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Moneda(this,bmp);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.BLACK);


        Drawable d = getResources().getDrawable(R.drawable.images);
        d.setBounds(0, 0, getWidth(), getHeight());
        d.draw(canvas);
        //setBackgroundDrawable(getResources().getDrawable(R.drawable.images));

       // moneda.onDraw(canvas);

        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
            Rect s1= sprite.rectangulo();
            Rect s2 = sprite2.rectangulo();
            boolean col=sprite.colision(s1, s2);
            if(col)
            {
                //gameOver( canvas);

            }


        }

        for (Moneda moneda : monedas) {
            moneda.onDraw(canvas);



        }
        sprite2.onDraw(canvas);
    }

    private void gameOver(Canvas canvas) {
        /*
        Paint p=new Paint();
        String text;
        p.setTextSize(canvas.getWidth()/7);
        p.setTextAlign(Paint.Align.CENTER);
        text = "game over";
        canvas.drawText(text,canvas.getWidth()/2,canvas.getHeight()/4,p);
        */

        Intent intent = new Intent(this.getContext(), GameOver.class);
        this.getContext().startActivity(intent);


    }

    public boolean onTouchEvent( MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){

            int actX= sprite2.getX();
            //Toast.makeText(getContext(),"hola "+actX,Toast.LENGTH_SHORT).show();
           // event.getButtonState();


                float y=event.getY();
                float x=event.getX();

                int resX=sprite2.getWidth()/2;
                int resY=sprite2.getHeight()/2;

                sprite2.obtenerCoor(x-resX,y-resY);





        }
        return true;
    }



}
