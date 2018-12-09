package basePackage.entity;

import basePackage.BaseEntity;

/**
 * 订单详情类
 * @author Administrator
 *
 */
public class OrdersDetail extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -171509562502519081L;

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
	
	private Integer count;
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
}
