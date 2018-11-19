package basePackage.service;

import com.github.pagehelper.PageInfo;

import basePackage.entity.Goods;

public interface GoodsService {
PageInfo<Goods> getGoodsByclass(Goods goods);
Goods getGoodsDetail(String id);
}
