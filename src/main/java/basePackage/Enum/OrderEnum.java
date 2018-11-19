package basePackage.Enum;

public enum OrderEnum {
	Closed("已关闭",-1),
	Unpay("待支付",0),
	Paid("已付款",1),
	Undeliver("待发货",2),
	Deliverd("已发货",3),
	Recived("已收货",4),
	OrderFinish("交易完成",5)
	;
	private String name;
	private int index;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	private OrderEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
}
