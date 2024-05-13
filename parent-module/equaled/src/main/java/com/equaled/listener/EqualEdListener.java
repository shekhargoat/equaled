package com.equaled.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EqualEdListener {

//	private final IEqualEdManager equalEdManager;

	private static final Logger logger = LoggerFactory.getLogger(EqualEdListener.class);
	static {
		logger.info("ChatbotListener active...");
	}

	/*public EqualEdListener(IEqualEdManager equalEdManager) {
		this.equalEdManager = equalEdManager;
	}*/

//	@JmsListener(destination = "Queues.BOT_QUEUE", containerFactory = "queue-container-factory")
	/*public void chatbotMessageListener(ComponentMessage message) {
		try{
			equalEdManager.processBotRequest(message);
		}catch (Exception e){
			e.printStackTrace();
		}
	}*/
	
}
