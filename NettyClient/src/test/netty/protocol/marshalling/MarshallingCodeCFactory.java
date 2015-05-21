package test.netty.protocol.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import test.netty.protocol.decoder.NettyMarshallingDecoder;
import test.netty.protocol.encoder.NettyMarshallingEncoder;
import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

public final class MarshallingCodeCFactory 
{
	public static NettyMarshallingDecoder buildMarshallingDecoder()
	{
		final MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory,configuration);
		NettyMarshallingDecoder decoder = new NettyMarshallingDecoder(provider,1024);
		return decoder;
	}
	public static NettyMarshallingEncoder buildMarshallingEncoder()
	{
		final MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory,configuration);
		NettyMarshallingEncoder encoder = new NettyMarshallingEncoder((MarshallerProvider) provider);
		return encoder;
	}
	
}
