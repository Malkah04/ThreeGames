package game;

import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.*;

public class AnimGlEventListener3 extends AnimListener implements KeyListener, GLEventListener {
    String textureNames[] = {"man1.png","Man2.png","Man3.png","Man4.png","a.png","b.png","c.png" ,"d.png" ,"e.png","f.png","g.png","h.png","i.png","j.png","k.png","l.png","m.png","n.png","o.png","p.png","q.png","r.png","s.png","t.png","u.png","v.png","w.png","x.png","y.png","z.png" ,"HealthB.png","Health.png","18.png","Back.png"};
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
//        int y=5;
        int x=5;
        for (int i = 0; i < w.length(); i++) {
        for (int j = 4; j <30 ; j++) {
                if(w.charAt(i)==textureNames[j].charAt(0)){
                    l.add(new Letter(x+=10,70,j,0,1));
                }
            }
        }
        for (int j = 4; j < 30; j++) {
            if (textureNames[j] != null && !textureNames[j].isEmpty()) {
                textureMap.put(textureNames[j].charAt(0), j);
            }
        }

    }
    Map<Character, Integer> textureMap = new HashMap<>();
    List<Letter>l=new ArrayList<>();
    String w="hello";

    @Override
    public void display(GLAutoDrawable gld) {
        boolean fn=false;
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);
            for (int i = 0; i < l.size(); i++) {
                Letter letter = l.get(i);
                DrawSprite(gl, letter.x, letter.y, 0, 1);
            }

            for (Letter l : flag) {
                DrawSprite(gl, l.x, l.y, l.textureIndex, 1);
            }

//        for (Letter letter : l) {
//            DrawSprite(gl, letter.x,letter.y, letter.textureIndex,1);
//        }

        for (Letter l:nonFlag) {
            DrawSprite(gl, l.x, l.y, l.textureIndex,1);
        }

         if(dn) {
             if (l.isEmpty()) {
                 DrawSprite(gl, 5, 40, textureNames.length - 6, 1); //y
                 DrawSprite(gl, 15, 40, 18, 1);//o
                 DrawSprite(gl, 25, 40, textureNames.length - 10, 1);//u
                 DrawSprite(gl, 40, 40, textureNames.length - 8, 1); //w
                 DrawSprite(gl, 50, 40, 12, 1); //i
                 DrawSprite(gl, 60, 40, textureNames.length - 17, 1); //n
                  nonFlag.clear();

             }
             else {
                 if (nonFlag.size() > 7) {
                     DrawSprite(gl, 5, 40, 10, 1); //g
                     DrawSprite(gl, 15, 40, 4, 1);//a
                     DrawSprite(gl, 25, 40, 16, 1);//m
                     DrawSprite(gl, 35, 40, 8, 1);//e
                     DrawSprite(gl, 50, 40, 18, 1);//o
                     DrawSprite(gl, 60, 40, textureNames.length - 9, 1);//v
                     DrawSprite(gl, 70, 40, 8, 1);//e
                     DrawSprite(gl, 80, 40, 21, 1);//r
                 }
             }

         }




    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    List<Letter>flag=new ArrayList<>();
    List<Letter>nonFlag=new ArrayList<>();
    int xx=0;
    int cnt=0;
    boolean dn=false;
    @Override
    public void keyTyped(KeyEvent e) {
        char c=e.getKeyChar();
        boolean flag1=false;
        for (int i = 0; i < l.size() ; i++) {
            if (nonFlag.size()<8&&c == textureNames[l.get(i).textureIndex].charAt(0)) {
                flag.add(new Letter(l.get(i).x, l.get(i).y, l.get(i).textureIndex, 1, 1));
                l.remove(l.get(i));
               flag1=true;
                break;
            }
        }
        if ( !flag1&&textureMap.containsKey(c)&&!l.isEmpty()&&cnt<=7) {
            int textureIndex = textureMap.get(c);
            nonFlag.add(new Letter(xx += 10, 10, textureIndex, 1, 1));
            cnt++;
        }
        if(nonFlag.size()<=8){
            dn=true;

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
