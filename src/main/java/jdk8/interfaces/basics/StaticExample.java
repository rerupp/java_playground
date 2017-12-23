package jdk8.interfaces.basics;

@SuppressWarnings("JavaDoc")
public interface StaticExample {

	@SuppressWarnings("UseOfSystemOutOrSystemErr")
	static void show(final String message) {
		System.out.println(message);
	}

}
