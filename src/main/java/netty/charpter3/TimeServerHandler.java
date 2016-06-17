package netty.charpter3;

import com.sun.jmx.snmp.Timestamp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf  buf=(ByteBuf) msg;
		byte[]bufs=new byte[buf.readableBytes()];
		buf.readBytes(bufs);
		String body=new String(bufs,"UTF-8");
		System.out.println("Server accept order="+body);
		String rep="QUERY TIME ORDER".equalsIgnoreCase(body)
		?new Timestamp(System.currentTimeMillis()).toString():"BAD QUERY";
		ByteBuf response=Unpooled.copiedBuffer(rep.getBytes());
		ctx.channel().writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}



}
