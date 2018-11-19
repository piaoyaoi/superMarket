package basePackage.entity;
/**
 * 商品评论
 * @author Administrator
 *
 */
public class GoodsComment {
	/**
	 * 评论内容
	 */
	private String comments; 
	
	/**
	 * 商品id
	 */
	private String goodsId;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
}
