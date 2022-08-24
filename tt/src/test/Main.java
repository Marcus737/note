package test;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//https://blog.csdn.net/chuyouyinghe/article/details/112402116
//https://www.renrendoc.com/paper/150368992.html
//http://www.javashuo.com/article/p-nixdcuvd-ga.html
//https://max.book118.com/html/2017/0601/110935459.shtm
public class Main {

    public static void main(String[] args) throws LineUnavailableException, IOException {
        int duration = 5; // sample for 5 seconds
        TargetDataLine line = null;
        // find a DataLine that can be read
        // (maybe hardcode this if you have multiple microphones)
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        for (int i = 0; i < mixerInfo.length; i++) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
            Line.Info[] targetLineInfo = mixer.getTargetLineInfo();
            if (targetLineInfo.length > 0) {
                line = (TargetDataLine) mixer.getLine(targetLineInfo[0]);
                break;
            }
        }
        if (line == null)
            throw new UnsupportedOperationException("No recording device found");
        AudioFormat af = new AudioFormat(11000, 8, 1, true, false);
        line.open(af);
        line.start();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int)af.getSampleRate() * af.getFrameSize()];
        long end = System.currentTimeMillis() + 1000 * duration;
        int len;
        while (System.currentTimeMillis() < end && ((len = line.read(buf, 0, buf.length)) != -1)) {
            baos.write(buf, 0, len);
        }
        line.stop();
        line.close();
        baos.close();
    }
}
