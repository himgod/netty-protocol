package test.netty.protocol.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import test.codec.pojo.SubscribeReq;
import test.netty.protocol.message.Header;
import test.netty.protocol.message.MessageType;
import test.netty.protocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter
{
	
	private String[] whiteList = {"127.0.0.1","192.168.0.112"};
	private Map<String,Boolean> nodeCheck = new HashMap<String,Boolean>();

//	public void channelRead(ChannelHandlerContext ctx,NettyMessage msg)
//	{
//		
//	}

	private NettyMessage buildLoginResponse(Object obj) 
	{
		// TODO Auto-generated method stub
		
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_RESP);
		message.setHeader(header);
		message.setBody(obj);
		return message;
	}

	public void ChannelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		nodeCheck.remove(ctx.channel().remoteAddress().toString());
		ctx.close();
		ctx.fireExceptionCaught(cause);
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("channelRead function...IN");
		NettyMessage message = (NettyMessage) msg;
		NettyMessage loginResp = null;
		if( message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ)
		{
			//InetSocketAddress remoteAddress = (InetSocketAddress)ctx.channel().remoteAddress();
			String nodeIndex = ctx.channel().remoteAddress().toString();
			System.out.println("remote address is:"+nodeIndex);
			if(nodeCheck.containsKey(nodeIndex))
			{
				loginResp = buildLoginResponse((byte)-1);
			}
			else
			{
				InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
				String ip = address.getAddress().getHostAddress();
				boolean isOk = false;
				for(String WIP:whiteList)
				{
					if( WIP.equals(ip) )
					{
						isOk = true;
						break;
					}
				}
				
				loginResp = isOk?buildLoginResponse((byte)0):buildLoginResponse((byte)-1);
				
				if( isOk )
				{
					System.out.println("Login is OK");
					nodeCheck.put(nodeIndex,true);
				}
			}
			
			String body = (String)message.getBody();
			System.out.println("Received message body from client is:"+body);
			ctx.writeAndFlush(loginResp);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
		
//		SubscribeReq req = new SubscribeReq();
//		req.setUserName("himgod");
//		req.setSubReqID(1);
//		req.setPhoneNumber("15929924725");
//		req.setProductName("Netty for book");
		//buildLoginResponse(req));
	}
	
}

