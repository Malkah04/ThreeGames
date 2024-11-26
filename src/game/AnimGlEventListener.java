package game;

import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class AnimGlEventListener extends AnimListener implements GLEventListener , MouseListener , MouseMotionListener {
    String textureNames[] = {"Man1.png","Man2.png","Man3.png","Man4.png","c.png","a.png","b.png" ,"d.png" ,"e.png","f.png","g.png","h.png","i.png","j.png","k.png","l.png","m.png","n.png","o.png","p.png","q.png","r.png","s.png","t.png","u.png","v.png","w.png","x.png","y.png","z.png" ,"HealthB.png","Health.png","ninja star.png","Back.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    int maxWidth=100;
    int maxHeight=100;

    @Override
    public void init(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
//        gl.glOrtho(0, 600, 0, 600,-1,1);


        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 26; i++) {
            letters.add(new Letter(0, 90, i + 4, 1, 1));
            letters.add(new Letter(0, 90, i + 4, 1, 1));
            cards.add(new Card(0, 90, textureNames.length - 3, 1, 1));
            cards.add(new Card(0, 90, textureNames.length - 3, 1, 1));
        }
        drawLetter();
        drawCard();
        shuffle();
    }
    List<Letter> letters = new ArrayList<>();
    List<Card> cards = new ArrayList<>();



    @Override
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);


        for(Letter letter : letters){
            DrawSprite(gl,letter.x,letter.y,letter.textureIndex ,1);
        }
        for(Card card : cards){
            if (card.getScaleX() > 0 && card.getScaleY() > 0) {
                DrawSprite(gl, card.getX(), card.getY(), card.getTextureIndex(), card.getScaleX());
            }
        }

    }
    public void drawLetter() {
        int pox = 0, poy = 0;
        for (Letter letter : letters) {
            letter.setX(pox);
            letter.setY(poy);
            pox += 15;
            if (pox >= 100) {
                poy += 12;
                pox = 0;
            }
        }
    }
    public void drawCard(){
        int pox = 0, poy = 0;
        for (Card card : cards) {
            card.setX(pox);
            card.setY(poy);
            pox += 15;
            if (pox >= maxWidth) {
                poy += 12;
                pox = 0;
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i <letters.size() ; i++) {
            int j=(int)(Math.random()*(letters.size()-1-i));
            int temp=letters.get(i).getTextureIndex();
            letters.get(i).setTextureIndex(letters.get(j).getTextureIndex());
            letters.get(j).setTextureIndex(temp);
        }
    }

    public void DrawBackground(GL gl){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length-1]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }


    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }


    int mousex=0,mousey=0;
    int cnt=0;
    Set<String> s=new HashSet<>();
    List<Position>l=new ArrayList<>();
    @Override
    public void mouseClicked(MouseEvent e) {
        mousex =(int) convertX(e.getX(),e.getComponent().getWidth(),0,100);
        mousey = (int) convertY(e.getY(),e.getComponent().getHeight(),0,100);

        for (int i = 0; i < cards.size(); i++) {
            Card card=cards.get(i);
            if (isWithinTolerance(mousex,mousey,card.getX(),card.getY(),7)) {
                System.out.println("mouse:"+mousex+" "+mousey);
                System.out.println("card:"+card.getX()+" "+card.getY());
               s.add(textureNames[letters.get(i).getTextureIndex()]);
               l.add(new Position(i,(int)card.getX()));
                card.setX(card.getX()+1000);
                cnt++;
                System.out.println("Count"+cnt);
                if(cnt>=2){
                    if(s.size()==2){
                        for (Position pos : l) {
                            cards.get(pos.x).setX(pos.y);
                        }
                    }
                        cnt=0;
                        s.clear();
                        l.clear();
//                    break;
                }
             break;
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public double convertX(double x, double screenWidth, double left, double right) {
        return left + (x / screenWidth) * (right - left);
    }

    public double convertY(double y, double screenHeight, double bottom, double top) {
        return top - (y / screenHeight) * (top - bottom);
    }
    public void DrawSprite(GL gl,double x, double y, int index, float scale){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated( x/(maxWidth/2.0) - 0.9, y/(maxHeight/2.0) - 0.9, 0);
        gl.glScaled(0.1*scale, 0.1*scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    private boolean isWithinTolerance(double x1, double y1, double x2, double y2, double tolerance) {
        return Math.abs(x1 - x2) <= tolerance && Math.abs(y1 - y2) <= tolerance;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        mousex =(int) convertX(e.getX(),e.getComponent().getWidth(),0,100);
//        mousey = (int) convertY(e.getY(),e.getComponent().getHeight(),0,100);
////        System.out.println(mousex+" "+mousey);
//        for (int i = 0; i < cards.size(); i++) {
//            Card card = cards.get(i);
//            if (isWithinTolerance(mousex, mousey, card.getX(), card.getY(), 5)) {
//                System.out.println("mouse:" + mousex + " " + mousey);
//                System.out.println("card:" + card.getX() + " " + card.getY());
//                System.out.println(mousex + " " + mousey);
//                card.setX(card.getX() + 1000);
////                break;
//            }
//        }
    }
    class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
