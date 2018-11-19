package basePackage.utils;

import org.springframework.stereotype.Component;

@Component
public class MessageBox {
	private String ms;
	private boolean success = true;
	private Object data;
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
