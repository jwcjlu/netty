package netty.charpter5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
public void bind(int port){
	EventLoopGroup group=new NioEventLoopGroup();
	EventLoopGroup childGroup=new NioEventLoopGroup();
	ServerBootstrap sb=new ServerBootstrap();
	sb.group(group, childGroup).channel(NioServerSocketChannel.class)
	.option(ChannelOption.SO_BACKLOG, 1024)
	.handler(new LoggingHandler(LogLevel.INFO))
	.childHandler(new ChannelInitializer<SocketChannel>() {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ByteBuf delimiter=Unpooled.copiedBuffer("$_".getBytes());
			ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
			ch.pipeline().addLast(new StringDecoder());
			ch.pipeline().addLast(new EchoServerHandler());
			
		}
	});
	try {
		ChannelFuture f=sb.bind(port).sync();
		f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		group.shutdownGracefully();
		childGroup.shutdownGracefully();
		e.printStackTrace();
	}
}
public static void main(String[] args) {
	new EchoServer().bind(8080);
}
}
