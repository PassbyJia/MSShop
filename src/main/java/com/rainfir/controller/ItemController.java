package com.rainfir.controller;

import com.rainfir.controller.BaseController;
import com.rainfir.controller.viewobject.ItemVO;
import com.rainfir.error.BusinessException;
import com.rainfir.model.ItemModel;
import com.rainfir.response.CommonReturnType;
import com.rainfir.service.ItemService;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    //获取商品列表接口
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItemList(){
        List<ItemModel> allItems = itemService.getAllItems();
        //model-->vo返回前端
        List<ItemVO> itemVOList = allItems.stream().map(itemModel -> {
            ItemVO itemVO = convertFromItemModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }
    //商品创建接口
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType create(@RequestParam("name")String name,
                                   @RequestParam("description")String description,
                                   @RequestParam("price")BigDecimal price,
                                   @RequestParam("stock")Integer stock,
                                   @RequestParam("imgurl")String imgurl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setDescription(description);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgurl);
        itemModel.setName(name);
        itemModel.setPrice(price);
        ItemModel model = itemService.create(itemModel);
        return CommonReturnType.create(model);
    }

    //获取商品接口
    @RequestMapping(value = "/getitem",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam("id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertFromItemModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertFromItemModel(ItemModel itemModel){
        if(itemModel==null) return null;
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!=null){
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoPrice());
            itemVO.setPromoStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS")));
            itemVO.setPromoStatus(itemModel.getPromoModel().getPromoStatus());
        }else{
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }
}
