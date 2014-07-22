package com.engineer.rpc;

import java.io.IOException;

public interface RPCClient {
	void start();
	void close() throws IOException;
	<T> T send(T msg) throws Exception;
}
