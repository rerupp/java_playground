package jdk8.lambdas.demo;

import java.util.Objects;

@SuppressWarnings("JavaDoc")
interface PojoConverter {

	static InPojo toInPojo(final OutPojo outPojo) {
		Objects.requireNonNull(outPojo, "OutPojo cannot be null...");
		return new InPojo(outPojo.getFirstName(), outPojo.getLastName());
	}

	static OutPojo toOutPojo(final InPojo inPojo) {
		Objects.requireNonNull(inPojo, "OutPojo cannot be null...");
		return new OutPojo(inPojo.getFirstName(), inPojo.getLastName());
	}

}
