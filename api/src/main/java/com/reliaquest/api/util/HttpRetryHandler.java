package com.reliaquest.api.util;


import java.util.function.Supplier;
import com.reliaquest.api.exception.ApiErrorCode;
import com.reliaquest.api.exception.ApiException;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HttpRetryHandler {
	 public static <T> T executeWithRetry(Supplier<T> action, int maxRetries, long initialWaitMillis) {
		 
	        long waitTime = initialWaitMillis;

	        for (int attempt = 1; attempt <= maxRetries; attempt++) {
	            try {
	                return action.get();
	            } catch (org.springframework.web.client.HttpClientErrorException.TooManyRequests e) {
	            	log.error("RetryAttempt : {}, errorMsg : {}", attempt, e.getMessage());
	                try {
	                    Thread.sleep(waitTime);
	                } catch (InterruptedException ie) {
	                    Thread.currentThread().interrupt();
	                    throw new RuntimeException(ie);
	                }

	                waitTime *= 2; // exponential backoff
	            }
	        }
	        log.error("Failed after retrying : {} times", maxRetries);
	        throw ApiException
	        		.builder()
	        		.apiErrorCode(ApiErrorCode.TOO_MANY_REQUESTS)
	        		.message("Failed after retrying : %s times".formatted(maxRetries)).build();

	    }

}
