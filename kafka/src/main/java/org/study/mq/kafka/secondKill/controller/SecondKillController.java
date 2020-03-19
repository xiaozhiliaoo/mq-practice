package org.study.mq.kafka.secondKill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.study.mq.kafka.secondKill.service.SecondKillService;

import java.util.HashMap;
import java.util.Map;

@Controller(value = "/")
public class SecondKillController {

    @Autowired
    private SecondKillService service;

    @RequestMapping(value = "getStock")
    @ResponseBody
    public Map<String, Object> getStock() {
        service.initStock();

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("goodsId", SecondKillService.goodsId);
        result.put("goodsStock", SecondKillService.goodsStock);
        return result;
    }

    @RequestMapping(value = "secondKillPage")
    public ModelAndView secondKillPage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("second-kill-detail");
        return mv;
    }

    @RequestMapping(value = "buy")
    @ResponseBody
    public Map<String, Object> buy() {
        Map<String, Object> result = new HashMap<>();

        if (service.buy()) {
            result.put("buyResult", true);
            result.put("msg", "秒杀成功");
        } else {
            result.put("buyResult", false);
            result.put("msg", "没有秒到该商品");
        }

        return result;
    }
}
