package game;

import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimGlEventListener2 extends AnimListener implements KeyListener {
    String textureNames[] = {"Man1.png","Man2.png","Man3.png","Man4.png","c.png","a.png","b.png" ,"d.png" ,"e.png","f.png","g.png","h.png","i.png","j.png","k.png","l.png","m.png","n.png","o.png","p.png","q.png","r.png","s.png","t.png","u.png","v.png","w.png","x.png","y.png","z.png" ,"HealthB.png","Health.png","ninja star.png","Back.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    int maxWidth=100;
    int maxHeight=100;
    List<Letter>letters = new ArrayList<Letter>();
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
            int x= (int)( Math.random()*(maxWidth-20)+10);
            int y= (int)( Math.random()*(maxHeight-20)+10);
            int ran= (int) (Math.random()*10+12);
            letters.add(new Letter(x,y,i+4,ran,1));
            System.out.println(x+"  "+10);
        }
    }
    long startTime = System.currentTimeMillis();
    int cnt=0;

    @Override
    public void display(GLAutoDrawable gld) {
        long currentTime = System.currentTimeMillis();
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);
        for (int i = 0; i < letters.size(); i++) {
            Letter letter= letters.get(i);
            long elapsedTime = currentTime - startTime;
            DrawSprite(gl,letter.x,letter.y, letter.textureIndex,1);
            if(elapsedTime>=letter.scaleX*1000){
                cnt++;
                letters.remove(i);
            }
        }
        if(letters.isEmpty()){
            if(cnt>cntuser) {
                DrawSprite(gl, 5, 10, 10, 1); //g
                DrawSprite(gl, 15, 10, 5, 1);//a
                DrawSprite(gl, 25, 10, 16, 1);//m
                DrawSprite(gl, 35, 10, 8, 1);//e
                DrawSprite(gl, 50, 10, 18, 1);//o
                DrawSprite(gl, 60, 10, textureNames.length - 9, 1);//v
                DrawSprite(gl, 70, 10, 8, 1);//e
                DrawSprite(gl, 80, 10, 21, 1);//r
            }
            else {
                DrawSprite(gl, 5, 10,textureNames.length-6, 1); //y
                DrawSprite(gl, 15, 10, 18, 1);//o
                DrawSprite(gl, 25, 10, textureNames.length-10, 1);//u
                DrawSprite(gl, 40, 10,textureNames.length-8, 1); //w
                DrawSprite(gl, 50, 10, 12, 1); //i
                DrawSprite(gl, 60, 10, textureNames.length-17, 1); //n


            }
        }

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    int cntuser=0;
    @Override
    public void keyTyped(KeyEvent e) {
      char c = e.getKeyChar();
      for (int i = 0; i < letters.size(); i++) {
          if(c==textureNames[letters.get(i).textureIndex].charAt(0)){
             letters.get(i).setX(1000);
              cntuser++;
          }
      }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void DrawSprite(GL gl, double x, double y, int index, float scale){
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
}
