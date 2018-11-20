package basePackage.utils;


public class MessageBox {
	private String ms;
	private boolean success = true;
	private Object data;
	
	public static MessageBox noMessageAndSuccess(Object data) {
		MessageBox msBox=new MessageBox();
		msBox.setData(data);
		return msBox;
	}
	public static MessageBox noMessageAndFail(Object data) {
		MessageBox msBox=new MessageBox();
		msBox.setData(data);
		msBox.setSuccess(false);
		return msBox;
	}
	public static MessageBox HasMessageAndFail(String msg,Object data) {
		MessageBox msBox=new MessageBox();
		msBox.setData(data);
		msBox.setSuccess(false);
		msBox.setMs(msg);
		return msBox;
	}
	
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
