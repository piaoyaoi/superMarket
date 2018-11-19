package basePackage.entity;

import basePackage.BaseEntity;

/**
 * 首页轮换图片
 * @author piaoyao
 *
 */
public class Banner  extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2182060219271461957L;
	/**
	 * 商品id
	 */
	private String goodsId;
	/**
	 * 图片
	 */
	private String imgUrl;
	/**
	 * 权重
	 */
	private int weight;
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}
