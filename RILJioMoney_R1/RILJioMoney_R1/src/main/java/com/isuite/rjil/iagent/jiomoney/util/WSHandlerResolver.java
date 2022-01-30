package com.isuite.rjil.iagent.jiomoney.util;


import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class WSHandlerResolver implements HandlerResolver {

	@SuppressWarnings("rawtypes")
	private List<Handler> chain;

	@SuppressWarnings("rawtypes")
	public WSHandlerResolver(String userName,long requestId) {
		chain = new ArrayList<Handler>();
		chain.add(new WSHandler(userName,requestId));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Handler> getHandlerChain(PortInfo arg0) {
		return chain;
	}

}
