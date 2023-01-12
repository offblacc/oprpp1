package hr.fer.oprpp1.hw08.jnotepadpp.statusbar;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Clock extends JLabel {
    private volatile String time;
    private volatile boolean stopRequested;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").withZone(ZoneId.systemDefault());

    public Clock() {
        super();
        setHorizontalAlignment(SwingConstants.RIGHT);
        updateTime();
        Thread t = new Thread(() -> {
            while (!stopRequested) {
                updateTime();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        });
        t.setDaemon(true);
        t.start();
    }

    private void updateTime() {
        time = formatter.format(LocalDateTime.now());
        SwingUtilities.invokeLater(() -> setText(time));
        repaint();
    }

    public void stop() {
        stopRequested = true;
    }

}
