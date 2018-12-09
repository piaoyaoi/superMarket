package basePackage.entity;

import java.util.List;

import basePackage.BaseEntity;
import basePackage.Enum.GoodsEnum;

/**
 * 商品类
 * @author piaoyaoi
 *
 */
public class Goods extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8582470959533012168L;

	/**
	 * 商品名称
	 */
	private String goodsName;
	
	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 商品价格
	 */
	private Double price;
	
	/**
	 * 商品主图
	 */
	private String mainImgUrl;
	
	/**
	 * 商品状态
	 * @return 0-正常 -1-下架 
	 */
	private Integer state = GoodsEnum.Normal.getIndex();
	
	/**
	 * 商品权重，值越小越靠前
	 * @return
	 */
	private Integer weight;
	
	/**
	 * 商品描述
	 * @return
	 */
	private String description;
	/**
	 * 销量
	 */
	private int sales=0 ;
	/**
	 * 商品分类
	 * @return
	 */
	private String classId;
	
	private List<GoodsImg> goodImg;
	
	/**
	 * 所属型号id
	 */
	private String sizeId;
	
	/**
	 * 该商品包邮界限 为null表示不包邮，订单多个商品则取平均值
	 */
	private double postLimit; 
	
	
	public double getPostLimit() {
		return postLimit;
	}

	public void setPostLimit(double postLimit) {
		this.postLimit = postLimit;
	}

	public String getSizeId() {
		return sizeId;
	}

	public void setSizeId(String sizeId) {
		this.sizeId = sizeId;
	}

	public List<GoodsImg> getGoodImg() {
		return goodImg;
	}

	public void setGoodImg(List<GoodsImg> goodImg) {
		this.goodImg = goodImg;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getMainImgUrl() {
		return mainImgUrl;
	}

	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}
	
}
