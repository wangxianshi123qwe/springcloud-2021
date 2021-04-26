package com.learn.springcloud.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/redexl")
    public String read(){
        System.out.println("进入方法");
        String file = "F://数据映射模板-四川农信社数据仓库系统映射关系（生产） - 副本.xlsx";

       /* // 这里 取出来的是 ExcelModel实体 的集合
        List<Object> readList = null;
        try {
            Sheet sheet = new Sheet(2, 3, MetadataDevMapping.class);
            readList = EasyExcelFactory.read(new FileInputStream(file), sheet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 存 ExcelMode 实体的 集合
        List<MetadataDevMapping> list = new ArrayList<MetadataDevMapping>();
        for (Object obj : readList) {
            list.add((MetadataDevMapping) obj);
        }*/
        return "ok";
    }
}
