package com.hq.service;

import com.github.pagehelper.PageInfo;
import com.hq.dto.AttachDto;
import com.hq.model.Attach;

/**
 * @description:
 * @author: Mr.Huang
 * @create: 2019-04-17 17:14
 **/
public interface AttachService {

    PageInfo<AttachDto> getAtts(int page, int limit);

    void addAttAch(Attach attach);
}
