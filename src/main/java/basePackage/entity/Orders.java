package basePackage.entity;

import java.util.List;

import basePackage.BaseEntity;

/**
 * 订单类
 * @author Administrator
 *
 */
public class Orders extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3331267331119841566L;
	
	/**
	 * 订单总金额
	 */
	private Double totalMoney;
	
	/**
	 * 订单状态
	 * -1 已关闭   0 - 待支付 1 - 已付款 2 -待发货 3-已发货(待收货) 4 已收货 5 交易完成   
	 */
	private Integer state;

	/**
	 * 详情集合
	 * @return
	 */
	private List<OrdersDetail> detailList;
	
	
	
	public List<OrdersDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrdersDetail> detailList) {
		this.detailList = detailList;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
