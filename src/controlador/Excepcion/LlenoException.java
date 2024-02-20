package controlador.Excepcion;

public class LlenoException extends Exception{
    /**
     * Creates a new instance of <code>EmptyException</code> without detail
     * message.
     */
    public LlenoException() {
    }

    /**
     * Constructs an instance of <code>EmptyException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public LlenoException(String msg) {
        super(msg);
    }
}
