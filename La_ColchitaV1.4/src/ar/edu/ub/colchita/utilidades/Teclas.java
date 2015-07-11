package ar.edu.ub.colchita.utilidades;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

/**
 * <b><big><u><code>enum</code> <code>Teclas</code>.</u></big></b>
 * <p>
 * Representa una lista limitada de valores (Cada tecla con su keystroke) que se utilizan durante la ejecuci&oacuten del programa.
 * <p>
 */

public enum Teclas {
	
    CTRLD  ("CONTROL-D", KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)),
    CTRLE  ("CONTROL-E", KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK)),
    CTRLS  ("CONTROL-S", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)),
    CTRLN  ("CONTROL-N", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

    private String nombreTecla;
    private KeyStroke keyStroke;

    private Teclas(String nombreTecla, KeyStroke keyStroke) {
        this.nombreTecla = nombreTecla;
        this.keyStroke = keyStroke;
    }

    public String getNombreTecla() {
        return nombreTecla;
    }

    public KeyStroke getKeyStroke() {
        return keyStroke;
    }

    @Override
    public String toString() {
        return nombreTecla;
    }
	
}