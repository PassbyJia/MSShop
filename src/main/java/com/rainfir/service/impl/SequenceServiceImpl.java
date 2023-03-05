package com.rainfir.service.impl;

import com.rainfir.dao.SequenceDOMapper;
import com.rainfir.dataobject.SequenceDO;
import com.rainfir.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceDOMapper sequenceDOMapper;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderId() {
        StringBuilder stringBuilder = new StringBuilder();
        //前八位时间
        LocalDateTime now = LocalDateTime.now();
        String nowData = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowData);
        //中间6位自增序列
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequence+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0;i<6-sequenceStr.length();i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);
        //后两位分库分表码，暂定为00
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
}
