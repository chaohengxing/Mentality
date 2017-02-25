package com.agjsj.mentality.global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HengXing on 2017/2/20.
 */
public class MyConfig {

    /**
     * 获取从今天往后五天的年月日
     *
     * @return
     */
    public static List<String> getTimeDates() {
        List<String> timeDates = new ArrayList<>();

        timeDates.add("2017-02-15");
        timeDates.add("2017-02-16");
        timeDates.add("2017-02-17");
        timeDates.add("2017-02-18");
        timeDates.add("2017-02-19");
        return timeDates;
    }

}
