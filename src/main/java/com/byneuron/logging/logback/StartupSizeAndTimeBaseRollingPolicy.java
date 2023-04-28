package com.byneuron.logging.logback;

import java.io.File;

import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;

public class StartupSizeAndTimeBaseRollingPolicy<E> extends SizeAndTimeBasedRollingPolicy<E> {
	
	FileSize maxFileSize;
	
	@Override
	public void start() {
		super.setMaxFileSize(new FileSize(-1)); 
		super.start();
		while(!super.isTriggeringEvent(new File(""), null)) { //setting FileSize to -1 + using a nonnull file forces the underlying policy into a rollover, we do have to wait until we get a true value
			try { //the triggerevent has some timebased thing which can result in false being returned which will then fail the rollover
				synchronized (this){
					this.wait(10);
				}
			} catch (InterruptedException e1) {
				throw new RuntimeException(e1);
			} 
		}
        try {
            rollover();
        } catch (RolloverFailure e) {
            //Do nothing
        }
        super.stop(); //stop the policy that we tricked
        super.setMaxFileSize(maxFileSize); //and start the proper one with correct file size
        
        super.start();
	}
	
	@Override
	public void setMaxFileSize(FileSize aMaxFileSize) {
        this.maxFileSize = aMaxFileSize;
    }
	
}
