package com.qhx.back;

import com.qhx.back.service.TraceService;
import com.qhx.back.util.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackApplicationTests
{

    @Autowired
    private TraceService traceService;


    @Autowired
    private HttpUtil httpUtil;

    @Test
    void contextLoads()
    {
        System.out.println(12);
    }

}
