package test.netty.protocol.encoder;

import java.util.List;

//import org.jboss.marshalling.Marshalling;





import java.util.Map;

import test.netty.protocol.marshalling.MarshallingCodeCFactory;
import test.netty.protocol.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
//import io.netty.handler.codec.marshalling.MarshallingEncoder;

public final class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage>
{

	NettyMarshallingEncoder marshallingEncoder;
	
	public NettyMessageEncoder()
	{
		System.out.println("NettyMessageEncoder Constructor....In");
		marshallingEncoder =  MarshallingCodeCFactory.buildMarshallingEncoder();
		
	}
	@Override
	protected void encode(ChannelHandlerContext ctx, NettyMessage msg,
			List<Object> out) throws Exception 
	{
		System.out.println("encoding...IN");
		// TODO Auto-generated method stub
		if( msg == null || msg.getHeader() == null )
		{
			throw new Exception("the encode message is null");
		}
		
		ByteBuf sendBuf = Unpooled.buffer();
		sendBuf.writeInt(msg.getHeader().getCrcCode());
		sendBuf.writeInt(msg.getHeader().getLength());
		sendBuf.writeLong(msg.getHeader().getSessionID());
		sendBuf.writeByte(msg.getHeader().getType());
		sendBuf.writeByte(msg.getHeader().getPriority());
		sendBuf.writeInt(msg.getHeader().getAttachment().size());
		
		String key = null;
		byte[] keyArray = null;
		Object value = null;
		
		for(Map.Entry<String, Object> param:msg.getHeader().getAttachment().entrySet())
		{
			key = param.getKey();
			keyArray = key.getBytes();
			sendBuf.writeInt(keyArray.length);
			sendBuf.writeBytes(keyArray);
			value = param.getValue();
			marshallingEncoder.encode(ctx, value, sendBuf);
		}
		
		key = null;
		keyArray = null;
		value = null;
		
		if( msg.getBody() != null )
		{
			marshallingEncoder.encode(ctx, msg.getBody(), sendBuf);
		}
		
		sendBuf.setInt(4, sendBuf.readableBytes());
		
		out.add(sendBuf);
		
		//ctx.writeAndFlush(out);
	}
	
}
