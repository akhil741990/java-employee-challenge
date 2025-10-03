package com.reliaquest.api.util;

import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import com.reliaquest.api.exception.ApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HttpRetryHandlerTest {

	@Test
	public void successWithoutRetryTest() {
		
		Supplier<String> mockAction = mock(Supplier.class);
		when(mockAction.get()).thenReturn("Success");
		assertEquals("Success", HttpRetryHandler.executeWithRetry(mockAction,3,1000l));
		verify(mockAction, times(1)).get();
		
	}
	
	@Test
    void oneRetryThenSuccessTest() {
        
        Supplier<String> action = mock(Supplier.class);
        
        HttpClientErrorException ex = HttpClientErrorException.create(
                HttpStatus.TOO_MANY_REQUESTS,
                "Too Many Requests",
                null,
                null,
                null 
        );
               
        when(action.get())
                .thenThrow(ex)
                .thenReturn("SUCCESS");
        
        String result = HttpRetryHandler.executeWithRetry(action, 3, 1);

        assertEquals("SUCCESS", result);
        verify(action, times(2)).get(); 
    }
	
	@Test
    void allRetriesFailedTest() {
        
        Supplier<String> action = mock(Supplier.class);
        HttpClientErrorException ex = HttpClientErrorException.create(
                HttpStatus.TOO_MANY_REQUESTS,
                "Too Many Requests",
                null,
                null,
                null 
        );
        when(action.get())
                .thenThrow(ex);

       
        ApiException e = assertThrows(ApiException.class,
                () -> HttpRetryHandler.executeWithRetry(action, 3, 1));

        assertEquals("Failed after retrying : 3 times", e.getMessage());
        verify(action, times(3)).get();
    }
	
}
