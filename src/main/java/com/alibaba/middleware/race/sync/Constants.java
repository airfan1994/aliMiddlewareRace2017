package com.alibaba.middleware.race.sync;

/**
 * 外部赛示例代码需要的常量 Created by wanshao on 2017/5/25.
 */
interface Constants {

    // ------------ 本地测试可以使用自己的路径--------------//

    // 工作主目录
    // String TESTER_HOME = "/Users/wanshao/work/middlewareTester";
  //   赛题数据
 //   String DATA_HOME = "E:/shx";
//    // 结果文件目录
   // String RESULT_HOME = "D:/canel";
   // String MIDDLE_HOME = "E:/shx";
//    String DATA_HOME = "C:/中间件/canal_data3";
////    // 结果文件目录
//    String RESULT_HOME = "D:/canel";
//    String MIDDLE_HOME = "C:/中间件/middle";
//    // teamCode
//    // 日志级别
//    
//    // 中间结果目录
//    String MIDDLE_HOME = "D:/canel";
    // server端口
    

    // ------------ 正式比赛指定的路径--------------//
    //// 工作主目录
    
    //// 赛题数据
     String DATA_HOME = "/home/admin/canal_data";
//    // 结果文件目录(client端会用到)
     String RESULT_HOME = "/home/admin/sync_results/80211g9dsw";
//    // 中间结果目录（client和server都会用到）
     String MIDDLE_HOME = "/home/admin/middle/80211g9dsw";

    // 结果文件的命名
    String TEAMCODE = "80211g9dsw";
    String LOG_LEVEL = "INFO";
    String TESTER_HOME = "/home/admin";
    Integer SERVER_PORT = 5527;
    String RESULT_FILE_NAME = "Result.rs";

}
