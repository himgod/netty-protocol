package test.netty.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import test.netty.protocol.message.Header;
import test.netty.protocol.message.MessageType;
import test.netty.protocol.message.NettyMessage;

public class LoginAuthReqHandler extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		System.out.println("sending login request");
		ctx.writeAndFlush(buildLoginReq());
	}
	
	
	private NettyMessage buildLoginReq()
	{
		// TODO Auto-generated method stub
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQ);
		message.setHeader(header);
		message.setBody("It is a request");
		return message;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object  msg)
	{
		// TODO Auto-generated method stub
		System.out.println("channelRead function...IN");
		NettyMessage message = (NettyMessage) msg;
		
		if( message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP)
		{
			System.out.println("Received from server response");
			ctx.fireChannelRead(msg);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
		if(message.getBody() != null )
		{
			System.out.println("response body is:"+message.getBody());
		}
		//ctx.fireChannelRead(msg);
	}

	

	public void ChannelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		ctx.close();
	}
}
