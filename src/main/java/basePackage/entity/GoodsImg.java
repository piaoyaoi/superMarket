package basePackage.entity;

import basePackage.BaseEntity;

/**
 * 商品图片集合
 * @author Administrator
 *
 */
public class GoodsImg extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8620305519914666246L;
	private String url; //图片链接地址
	private String goodsId; //商品id
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	
}
