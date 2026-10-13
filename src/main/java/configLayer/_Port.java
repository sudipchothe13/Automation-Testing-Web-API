package configLayer;

public enum _Port {
	
	GATEWAY_PORT(9000);

	private int port;

	_Port(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}
}
