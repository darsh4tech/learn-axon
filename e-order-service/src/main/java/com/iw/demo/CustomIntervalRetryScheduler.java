package com.iw.demo;

import org.axonframework.commandhandling.NoHandlerForCommandException;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;

public class CustomIntervalRetryScheduler extends IntervalRetryScheduler {

	protected CustomIntervalRetryScheduler(Builder builder) {
		super(builder);
	}

	@Override
	protected boolean isExplicitlyNonTransient(Throwable failure) {
		//retry in case command handler in other microservice and it is down  
		if(failure instanceof NoHandlerForCommandException)
			return false;
		else
			return super.isExplicitlyNonTransient(failure);
	}

}
