package jdk8.lambdas.demo;

@SuppressWarnings("JavaDoc")
class OutPojo {

	private final String firstName;
	private final String lastName;

	OutPojo(final String firstName, final String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	String getFirstName() {
		return firstName;
	}

	String getLastName() {
		return lastName;
	}

}
