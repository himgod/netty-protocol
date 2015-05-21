package test.netty.protocol.server;

import test.netty.protocol.decoder.NettyMessageDecoder;
import test.netty.protocol.encoder.NettyMessageEncoder;
import test.netty.protocol.handler.HeartBeatRespHandler;
import test.netty.protocol.handler.LoginAuthReqHandler;
import test.netty.protocol.handler.LoginAuthRespHandler;
import test.netty.protocol.message.NettyConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyServer 
{
	public void bind(int port) throws InterruptedException
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try
		{
			ServerBootstrap boot = new ServerBootstrap();
		
			boot.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChildChannelHandler());
		
			ChannelFuture future = boot.bind(port).sync();
			System.out.println("Netty time Server started at port:"+port);
		
			future.channel().closeFuture().sync();
		}
		finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4,-8,0));
			ch.pipeline().addLast(new NettyMessageEncoder());
			ch.pipeline().addLast(new ReadTimeoutHandler(50));
			ch.pipeline().addLast(new LoginAuthRespHandler());
			ch.pipeline().addLast(new HeartBeatRespHandler());
		}
		
	}
	
	public static void main(String[] args)
	{
		try 
		{
			new NettyServer().bind(NettyConstant.REMOTE_PORT);;
		} 
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
	


