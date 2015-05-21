package test.netty.protocol.handler;

import java.util.concurrent.TimeUnit;

import test.netty.protocol.message.Header;
import test.netty.protocol.message.MessageType;
import test.netty.protocol.message.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.ScheduledFuture;

public class HeartBeatReqHandler extends ChannelInboundHandlerAdapter
{
	private final byte HEARTBEAT_MSG = 1;
	private volatile ScheduledFuture<?> heartBeat;
	
	
	private NettyMessage buildHeartBeat()
	{
		// TODO Auto-generated method stub
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.HEARTBEAT_REQ);
		message.setHeader(header);
		return message;
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object  msg)
	{
		System.out.println("HeartBeatReq.channelRead()....");
		NettyMessage message = (NettyMessage)msg;
		//shakehands success,and send heartbeat message
		if( message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP)
		{
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx),0,5000,TimeUnit.MILLISECONDS);
			
		}
		else if( message.getHeader() != null && message.getHeader().getType() == MessageType.HEARTBEAT_RESP)
		{
			System.out.println("Client receive server heart beat message:--->"+message);
		}
		else
		{
			ctx.fireChannelRead(msg);
		}
	}
		

	private class HeartBeatTask implements Runnable
	{
		private final ChannelHandlerContext ctx;
		public HeartBeatTask(final ChannelHandlerContext ctx)
		{
			this.ctx = ctx;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			NettyMessage heartBeat = buildHeartBeat();
			System.out.println("Client send heart beat message to server:----"+heartBeat);
			ctx.writeAndFlush(heartBeat);
		}
		
		
	}
	public void ChannelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause)
	{
		if( heartBeat != null )
		{
			heartBeat.cancel(true);
			heartBeat = null;
		}
		ctx.fireExceptionCaught(cause);
		
	}
}
