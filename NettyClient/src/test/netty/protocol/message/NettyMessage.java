package test.netty.protocol.message;

public final class NettyMessage 
{
	private Header header;
	private Object body;
	
	public final Header getHeader()
	{
		return header;
	}

	public final Object getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "NettyMessage [header=" + header + ", body=" + body + "]";
	}

	public final void  setBody(Object body) {
		this.body = body;
	}

	public final void setHeader(Header header) {
		this.header = header;
	}
	
	
}
