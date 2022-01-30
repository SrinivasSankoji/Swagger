package com.isuite.rjil.iagent.sso;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class RemoveUIDListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent evt) {

	}

	@Override
	public void contextInitialized(ServletContextEvent evt) {

		TaskRepeating repeating=new TaskRepeating();
		repeating.start();

	}

}
