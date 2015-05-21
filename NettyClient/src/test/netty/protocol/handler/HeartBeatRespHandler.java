package test.netty.protocol.handler;

import test.netty.protocol.message.Header;
import test.netty.protocol.message.MessageType;
import test.netty.protocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter
{

	private NettyMessage buildHeartBeat()
	{
		// TODO Auto-generated method stub
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTBEAT_RESP);
		message.setHeader(header);
		return message;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception 
	{
		// TODO Auto-generated method stub
		//super.channelRead(ctx, msg);
		NettyMessage message = (NettyMessage)msg;
		if( message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_REQ )
		{
			System.out.println("Server Receive from client heart beat message:--->"+message);
			NettyMessage heartbeat = buildHeartBeat();
			System.out.println("Send heart beat response message to client:--->"+heartbeat);
			ctx.writeAndFlush(heartbeat);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception 
	{
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception 
	{
		// TODO Auto-generated method stub
		//super.exceptionCaught(ctx, cause);
		ctx.close();
	}
	
}
