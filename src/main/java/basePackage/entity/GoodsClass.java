package basePackage.entity;

import java.util.List;

import basePackage.BaseEntity;

/**
 * 商品分类
 * @author piaoyao
 *
 */
public class GoodsClass extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6154568329493791994L;
	/**
	 * 分类名称
	 */
	private String className; 
	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}

	/**
	 * 该分类下商品
	 */
	private List<Goods>goodsList;
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
