package com.sun;

import com.sun.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insuredCar")
public class TestController {

    @Autowired
    private ITestService testService;

    @PostMapping("read")
    public Object read() {
        return testService.read();
    }

    @PostMapping("write")
    public Object write() {
        return testService.write();
    }
}
