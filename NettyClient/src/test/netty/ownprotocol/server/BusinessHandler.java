package test.netty.ownprotocol.server;

import test.netty.ownprotocol.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BusinessHandler extends ChannelInboundHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception
	{
		// TODO Auto-generated method stub
//		super.channelRead(ctx, msg);
		System.out.println("reading from client:");
		Person person = (Person)msg;
		System.out.println(person);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception 
	{
		// TODO Auto-generated method stub
//		super.channelReadComplete(ctx);
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}
	
}
