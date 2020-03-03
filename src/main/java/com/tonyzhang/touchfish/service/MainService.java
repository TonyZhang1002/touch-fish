package com.tonyzhang.touchfish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class MainService {

    @Autowired
    private WebPipeline wp;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 0/1 * * * *")
    public void scheduled() {
        System.out.println("----Mission Start : " + dateFormat.format(new Date()));
        Spider spider = Spider.create(new WebProcessor());
        spider.addUrl("http://www.v2ex.com/?tab=hot");
        spider.addPipeline(wp);
        spider.thread(5);
        spider.setExitWhenComplete(true);
        spider.start();
        spider.stop();
    }
}
