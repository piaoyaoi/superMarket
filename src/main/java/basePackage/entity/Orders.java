package basePackage.entity;

import java.util.Date;
import java.util.List;

import basePackage.BaseEntity;

/**
 * 订单类
 * @author Administrator
 *
 */
public class Orders extends BaseEntity{
	
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	/**
	 * 预计到达日期
	 */
	private Date mayArriveDate;
	
	/**
	 * 实际到达日期
	 */
	private Date realyArriveDate;
	/**
	 * 订单备注
	 * @return
	 */
	private String remark;
	
	/**
	 * 发货日期
	 */
	private Date deliverDate;
	
	/**
	 * openid 唯一标识买家
	 * @return
	 */
	private String openid;
	
	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
