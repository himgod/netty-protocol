package test.netty.protocol.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import org.jboss.marshalling.ByteBufferOutput;
import org.jboss.marshalling.ByteOutput;
import org.jboss.marshalling.Marshaller;

import test.codec.serializable.netty.marshalling.MarshallingCodeCFactory;

public class NettyMarshallingEncoder extends MarshallingEncoder
{
	private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
	
	//Marshaller marshaller;
	
	public NettyMarshallingEncoder(MarshallerProvider provider)
	{
		//marshaller = (Marshaller) MarshallingCodeCFactory.buildMarshallingDecoder();
		super(provider);
	}
	
	public void encode(ChannelHandlerContext ctx,Object msg,ByteBuf out) throws Exception
	{
		//int lengthPos = out.writerIndex();
		//out.writeBytes(LENGTH_PLACEHOLDER);
		//ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
		//marshaller.start(output);
		super.encode(ctx, msg, out);
	}
}
