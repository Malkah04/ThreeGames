package game;
import com.sun.opengl.util.*;
import java.awt.*;
import java.awt.event.KeyListener;
import javax.media.opengl.*;
import javax.swing.*;

public class Anim extends JFrame {

    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i%4);
//        }
        new Anim();
    }


    public Anim() {
        GLCanvas glcanvas;
        Animator animator;
        AnimGlEventListener listener = new AnimGlEventListener();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        glcanvas.addMouseMotionListener(listener);
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        animator.start();
//        JButton st;
//        st = new JButton("Start");
//        st.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(st.getText() == "Start") {
//                    animator.start();
//                    st.setText("stop");
//                }
//                else {
//                    animator.stop();
//                    st.setText("Start");
//                }
//            }
//        });
//        add(st, BorderLayout.SOUTH);
//        animator.start();


        setTitle("Anim Test");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
//        JPanel buttonPanel = new JPanel();
//        JButton jb = new JButton("Start");
//        buttonPanel.add(jb);
//        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}