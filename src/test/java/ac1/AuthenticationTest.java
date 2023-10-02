package ac1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class AuthenticationTest {
	Authentication authentication;

	@Test
	public void logarUsuarioNoSistema() {
        // Arrange
        Authentication sut = new Authentication();
        
        // Action
        ValidationResult isValidResult = sut.validate("Admin", "adminPassword ");
        boolean isCredentialsValid = isValidResult.isValid();
        String errorMessage = isValidResult.getErrorMessage();
        
        // Assert
        assertEquals(true, isCredentialsValid);
        assertNull(errorMessage);
	 }
	
	@Test
	public void erroCredenciaisLogarUsuarioNoSistema() {
		 // Arrange
        Authentication sut = new Authentication();
        
        // Action
        ValidationResult isValidResult = sut.validate("Admin", "wrongPassword");
        boolean isValidCredentials = isValidResult.isValid();
        String errorMessage = isValidResult.getErrorMessage();
        
        // Assert
        assertEquals(false, isValidCredentials);
        assertEquals("Usuário ou Senha inválidos!", errorMessage);
	 }
	
	@Test
	public void bloqueioTemporarioCredencialInvalida() throws InterruptedException {
        // Arrange
        int triesLimit = 6;
        Authentication sut = new Authentication();
        
        // Action
        for (int i = 0; i <= triesLimit; i++) {
        	ValidationResult isValidCredentials = sut.validate("Admin", "wrongPassword");
        	
        	// Assert
        	if (i == triesLimit) {
        		assertEquals(false, isValidCredentials.isValid());
        	    assertEquals("Credencial bloqueada temporariamente, tente novamente em 3 horas.", isValidCredentials.getErrorMessage());
        	} else {
        		assertEquals(false, isValidCredentials.isValid());
        		assertEquals("Usuário ou Senha inválidos!", isValidCredentials.getErrorMessage());        		
        	}
        }
	}

	@Test
    public void testMissingCredentials() {
        // Arrange
        Authentication sut = new Authentication();

        // Action and Assert
        assertValidationResult(sut.validate("", null), false, "Usuário e Senha são obrigatórios");
        assertValidationResult(sut.validate(null, null), false, "Usuário e Senha são obrigatórios");
        assertValidationResult(sut.validate("", ""), false, "Usuário e Senha são obrigatórios");
        assertValidationResult(sut.validate(null, ""), false, "Usuário e Senha são obrigatórios");
        assertValidationResult(sut.validate("   ", " "), false, "Usuário e Senha são obrigatórios");
    }

    private void assertValidationResult(ValidationResult result, boolean expectedIsValid, String expectedErrorMessage) {
        assertEquals(expectedIsValid, result.isValid());
        assertEquals(expectedErrorMessage, result.getErrorMessage());
    }

}
