package com.agjsj.mentality.param;

import com.agjsj.mentality.bean.appoint.TimeTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MyPC on 2016/11/5.
 */

public class ConstatParam {
    //时间模板
    public static Map<Integer, TimeTemplate> timeTemplateMap = new HashMap<>();

    static {
        TimeTemplate template = new TimeTemplate();
        template.setTimeName("8:00-9:00");
        timeTemplateMap.put(0, template);

        TimeTemplate template1 = new TimeTemplate();
        template1.setTimeName("9:00-10:00");
        timeTemplateMap.put(1, template1);

        TimeTemplate template2 = new TimeTemplate();
        template2.setTimeName("10:00-11:00");
        timeTemplateMap.put(2, template2);

        TimeTemplate template3 = new TimeTemplate();
        template3.setTimeName("11:00-12:00");
        timeTemplateMap.put(3, template3);

        TimeTemplate template4 = new TimeTemplate();
        template4.setTimeName("14:00-15:00");
        timeTemplateMap.put(4, template4);

        TimeTemplate template5 = new TimeTemplate();
        template5.setTimeName("15:00-16:00");
        timeTemplateMap.put(5, template5);

    }
}
