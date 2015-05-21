package test.netty.ownprotocol.client;

import java.net.URI;
import java.net.URISyntaxException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

public class Client 
{
	public void connnect(String host,int port)
	{
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			Bootstrap boot = new Bootstrap();
			boot.group(workerGroup);
			boot.channel(NioSocketChannel.class);
			boot.option(ChannelOption.SO_KEEPALIVE, true);
			boot.handler(new ChannelInitializer(){

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new PersonEncoder());
					ch.pipeline().addLast(new ClientInitHandler());
					
				}				
			});
			
			ChannelFuture future = boot.connect(host,port).sync();
			//future.channel().flush();
			future.channel().closeFuture().sync();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args)
	{
		Client client = new Client();
		client.connnect("127.0.0.1", 8989);
	}
}
