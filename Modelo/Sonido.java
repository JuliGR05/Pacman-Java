package Modelo;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sonido {

    private static final Map<String, Clip> clipsActivos = new HashMap<>();

    // Clip reutilizable para pellets (evita saturar hilos)
    private static Clip clipPellet = null;

    /**
     * Reproduce el sonido de pellet reutilizando un solo Clip (liviano y rápido).
     */
    public static void reproducirPellet() {
        try {
            if (clipPellet == null || !clipPellet.isOpen()) {
                URL url = Sonido.class.getResource("/Recursos/waka.wav");
                if (url == null) return;
                AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                clipPellet = AudioSystem.getClip();
                clipPellet.open(audio);
            }
            if (clipPellet.isRunning()) clipPellet.stop();
            clipPellet.setFramePosition(0);
            clipPellet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reproduce un sonido una sola vez.
     */
    public static void reproducir(String ruta) {
        reproducirConVolumen(ruta, 1.0f);
    }

    /**
     * Reproduce un sonido una sola vez con volumen reducido (0.0 = mudo, 1.0 = máximo).
     */
    public static void reproducirConVolumen(String ruta, float volumen) {
        new Thread(() -> {
            try {
                URL url = Sonido.class.getResource(ruta);
                if (url == null) {
                    System.out.println("No se encontró el archivo: " + ruta);
                    return;
                }
                AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);

                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log10(Math.max(volumen, 0.0001f)) * 20);
                    dB = Math.max(gain.getMinimum(), Math.min(dB, gain.getMaximum()));
                    gain.setValue(dB);
                }

                clip.start();
                clip.addLineListener(e -> {
                    if (e.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Reproduce un sonido en loop continuo con un nombre clave para poder detenerlo.
     */
    public static void reproducirLoop(String nombre, String ruta) {
        detener(nombre);
        new Thread(() -> {
            try {
                URL url = Sonido.class.getResource(ruta);
                if (url == null) {
                    System.out.println("No se encontró el archivo: " + ruta);
                    return;
                }
                AudioInputStream audio = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                clipsActivos.put(nombre, clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Detiene un clip en loop por su nombre clave.
     */
    public static void detener(String nombre) {
        Clip clip = clipsActivos.remove(nombre);
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Detiene todos los sonidos activos.
     */
    public static void detenerTodo() {
        if (clipPellet != null && clipPellet.isRunning()) {
            clipPellet.stop();
        }
        for (Clip clip : clipsActivos.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        }
        clipsActivos.clear();
    }
}
