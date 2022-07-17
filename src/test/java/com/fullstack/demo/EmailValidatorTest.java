package com.fullstack.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

	private final EmailValidator underTest = new EmailValidator();
	
	@Test
	public void itShouldValidateCorrectEmail() {
		assertThat(underTest.test("Hello@gmail.com")).isTrue();
	}

	@Test
	public void itShouldValidateIncorrectEmail() {
		assertThat(underTest.test("Hellogmail.com")).isFalse();
	}

	@Test
	public void itShouldValidateIncorrectEmailNoDot() {
		assertThat(underTest.test("Hellogmailcom")).isFalse();
	}
}
