import java.awt.*;
import java.awt.event.*;

public class Main extends Frame implements Runnable, ActionListener, WindowListener {
    private String whichThread;
    protected static Button bStart;
    protected static Button bStop;
    protected static Label lProgress;
    protected static boolean stopped;

    public Main(String whichThread) {
        super();
        this.whichThread = whichThread;
        if (!"main".equals(whichThread)) {
            return;
        }
        addWindowListener(this);
        setLayout(null);

        // setResizable();
        setSize(320, 200);
        bStart = new Button("Start");
        bStart.setBounds(80, 120, 60, 20);
        bStart.addActionListener(this);
        add(bStart);
        bStop = new Button("Stop");
        bStop.setBounds(200, 120, 60, 20);
        bStop.addActionListener(this);
        add(bStop);
        lProgress = new Label("0%");
        lProgress.setBounds(155, 60, 40, 20);
        add(lProgress);
        bStart.setEnabled(true);
        bStop.setEnabled(false);
        setVisible(true);
    }

    public void run() {
        stopped = false;
        while(!bStart.isEnabled()) {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
                if (stopped) {
                    break;
                }
                if(i >= 50){
                    bStart.setBackground(Color.BLUE);
                    bStop.setBackground(Color.BLUE);
                }
                lProgress.setText((i + 1) + "%");
            }
            for (int i = 100; i > 0; i--) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
                if (stopped) {
                    break;
                }
                if(i < 50){
                    bStart.setBackground(Color.RED);
                    bStop.setBackground(Color.RED);
                }
                lProgress.setText(i + "%");
            }
        }
    }

    public static void main(String args[]) {
        new Main("main");
    }

    public void actionPerformed(ActionEvent evt) {
        String tmp = evt.getActionCommand();
        if (tmp.equals("Start")) {
            bStart.setEnabled(false);
            Main main = new Main("compute");
            new Thread(main).start();
            bStop.setEnabled(true);
        } else if (tmp.equals("Stop")) {
            stopped = true;
            bStart.setEnabled(true);
            bStop.setEnabled(false);
            bStart.setBackground(null);
            bStop.setBackground(null);
            lProgress.setText(0 + "%");
        }
    }

    public void windowDeiconified(WindowEvent evt) {
    }

    public void windowClosed(WindowEvent evt) {
    }

    public void windowDeactivated(WindowEvent evt) {
    }

    public void windowClosing(WindowEvent evt) {
        System.exit(0);
    }

    public void windowActivated(WindowEvent evt) {
    }

    public void windowIconified(WindowEvent evt) {
    }

    public void windowOpened(WindowEvent evt) {
    }
}