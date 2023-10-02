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
        ValidationResult isValidResult = sut.validate("Admin", "adminPassword");
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
        assertEquals("Invalid user or password", errorMessage);
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
        		assertEquals("Invalid user or password", isValidCredentials.getErrorMessage());        		
        	}
        }
	}

	@Test
	public void credencialFaltanteAoAutenticar() {
		 // Arrange
        Authentication sut = new Authentication();
        
        // Action
        ValidationResult isValidResult = sut.validate("", null);
        ValidationResult isValidResult2 = sut.validate(null, null);
        ValidationResult isValidResult3 = sut.validate("", "");
        ValidationResult isValidResult4 = sut.validate(null, "");
        ValidationResult isValidResult5 = sut.validate(" ", " ");

        boolean isValidCredentials = isValidResult.isValid();
        String errorMessage = isValidResult.getErrorMessage();
        
        boolean isValidCredentials2 = isValidResult2.isValid();
        String errorMessage2 = isValidResult2.getErrorMessage();
        
        boolean isValidCredentials3 = isValidResult3.isValid();
        String errorMessage3 = isValidResult3.getErrorMessage();
        
        boolean isValidCredentials4 = isValidResult4.isValid();
        String errorMessage4 = isValidResult4.getErrorMessage();
        
        boolean isValidCredentials5 = isValidResult5.isValid();
        String errorMessage5 = isValidResult5.getErrorMessage();
        
        // Assert
        assertEquals(false, isValidCredentials);
        assertEquals("Usuário e Senha são obrigatórios", errorMessage);
        
        assertEquals(false, isValidCredentials2);
        assertEquals("Usuário e Senha são obrigatórios", errorMessage2);
        
        assertEquals(false, isValidCredentials3);
        assertEquals("Usuário e Senha são obrigatórios", errorMessage3);
        
        assertEquals(false, isValidCredentials4);
        assertEquals("Usuário e Senha são obrigatórios", errorMessage4);
        
        assertEquals(false, isValidCredentials5);
        assertEquals("Usuário e Senha são obrigatórios", errorMessage5);
	 }

}
