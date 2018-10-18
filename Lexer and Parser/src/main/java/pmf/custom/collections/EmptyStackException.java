package pmf.custom.collections;
/**
 * Implementacija posebne iznimke izvedene iz RuntimeException
 * @author Borna Jelić PMF
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 221312;
	
	public EmptyStackException(String message) {
		super(message);
	}
}
