package com.hq.utils;

import com.hq.model.Meta;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by huang on 19/3/2019.
 */
@Component
public class AdminCommons
{
    /**
     * 判断category和cat的交集
     */
    public static boolean exist_cat(Meta category, String cats){
        String[]  arr = StringUtils.split(cats, ",");
        if (null != arr && arr.length > 0){
            for (String c : arr){
                if (c.trim().equals(category.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    private static final String[] COLORS = {"default", "primary", "success", "info", "warning", "danger", "inverse", "purple", "pink"};

    public static String rand_color() {
        int r = ToolUtil.rand(0, COLORS.length - 1);
        return COLORS[r];
    }
}
