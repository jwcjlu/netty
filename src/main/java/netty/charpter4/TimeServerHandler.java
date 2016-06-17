package netty.charpter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
	private int counter;

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*		ByteBuf buf=(ByteBuf) msg;
		byte[]req=new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body=new String(req,"UTF-8" ).substring(0,req.length-System.getProperty("line.separator").length());*/
		String body=(String) msg;
		System.out.println("this time server receive "+body+";the counter is:"+ ++counter);
		String currentTime=body.equalsIgnoreCase("QUERY TIME ORDER")?new java.util.Date(System.currentTimeMillis()).toString():"BAD QUERY";
		currentTime=currentTime+System.getProperty("line.separator");
		ByteBuf resp=Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
		
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

}
