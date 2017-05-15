package com.akash.design.patterns;

interface Request {
	int getValue();
}

class ActualRequest implements Request {
	private int value;

	ActualRequest(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

abstract class RequestHandler {
	private RequestHandler nextHandler;

	protected void setNextHandler(RequestHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	protected RequestHandler getNextHandler() {
		return nextHandler;
	}

	protected abstract boolean handle(Request req);

	public void handleCor(Request req) {
		boolean result = handle(req);
		if (!result)
			return;
		while (getNextHandler() != null) {
			boolean nextResult = nextHandler.handle(req);
			if (nextResult) {
				nextHandler = nextHandler.getNextHandler();
			} else {
				break;
			}
		}
	}
}

class RequestHandlerOne extends RequestHandler {
	protected boolean handle(Request req) {
		System.out.println("One");
		if (req.getValue() >= 0) {
			return true;
		}
		return false;
	}
}

class RequestHandlerTwo extends RequestHandler {
	protected boolean handle(Request req) {
		System.out.println("Two");
		if (req.getValue() > 0) {
			return true;
		}
		return false;
	}
}

class RequestHandlerThree extends RequestHandler {
	protected boolean handle(Request req) {
		System.out.println("Three");
		if (req.getValue() == 0) {
			return true;
		}
		return false;
	}
}

public class ChainOfResponsibility {
	public static void main(String args[]) {
		RequestHandlerOne h1 = new RequestHandlerOne();
		RequestHandlerTwo h2 = new RequestHandlerTwo();
		RequestHandlerThree h3 = new RequestHandlerThree();
		h1.setNextHandler(h2);
		h2.setNextHandler(h3);
		h1.handleCor(new ActualRequest(2));
	}
}