package basePackage.entity;

import java.util.Date;

/**
 * 订单详情类
 * @author Administrator
 *
 */
public class OrdersDetail {
	/**
	 * 订单唯一标识
	 */
	private String orderId;
	
	/**
	 * 商品id
	 */
	private String goodsId;
	
	/**
	 * 订单商品
	 */
	private Goods goods;
	
	/**
	 * 发货方式
	 */
	private String deliveWay;
	
	/**
	 * 发货日期
	 */
	private Date deliverDate;
	
	/**
	 * 预计到达日期
	 */
	private Date mayArriveDate;
	
	/**
	 * 实际到达日期
	 */
	private Date realyArriveDate;
	
	/**
	 * 备注
	 * @return
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getDeliveWay() {
		return deliveWay;
	}

	public void setDeliveWay(String deliveWay) {
		this.deliveWay = deliveWay;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Date getMayArriveDate() {
		return mayArriveDate;
	}

	public void setMayArriveDate(Date mayArriveDate) {
		this.mayArriveDate = mayArriveDate;
	}

	public Date getRealyArriveDate() {
		return realyArriveDate;
	}

	public void setRealyArriveDate(Date realyArriveDate) {
		this.realyArriveDate = realyArriveDate;
	}
	
	
}
