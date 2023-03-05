package com.rainfir.service.impl;

import com.rainfir.dao.PromoDOMapper;
import com.rainfir.dataobject.PromoDO;
import com.rainfir.model.PromoModel;
import com.rainfir.service.PromoService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //通过itemId从数据库拿到PromoDO
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        if(promoDO==null) return null;
        //dao-->model
        PromoModel promoModel = convertFromDataObject(promoDO);

        //判断当前时间是否秒杀活动即将开始或正在进行
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setPromoStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setPromoStatus(3);
        }else{
            promoModel.setPromoStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO==null) return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setPromoPrice(new BigDecimal(promoDO.getPromoPrice()));
        return promoModel;
    }


}
