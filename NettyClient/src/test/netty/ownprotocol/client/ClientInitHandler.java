package test.netty.ownprotocol.client;

import test.netty.ownprotocol.Person;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientInitHandler extends ChannelInboundHandlerAdapter
{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
//		super.channelActive(ctx);
		System.out.println("send person...");
		Person person = new Person();
		person.setName("himgod");
		person.setAge(24);
		person.setSex("male");
		
		ctx.write(person);
		ctx.flush();
		//ctx.fireChannelActive();
	}
	
}
