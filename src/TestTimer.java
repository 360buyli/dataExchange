
/**
 * <p>Title: 知识管理索引的定时器</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 南方科能</p>
 * @author 毕元堂
 * @version 1.0
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.sql.ResultSet;
import java.io.PrintWriter;

public class TestTimer {

  private final Timer timer = new Timer();
  public TestTimer() {
  }

  public void start() {
     timer.schedule(new TimerTask() {
           public void run() {
             TalentMakeIndex maker = new TalentMakeIndex();
              maker.exeIt();
            }
       },10*1000,1000*60*60);//定时器启动10秒后,每隔一小时再运行一次run();
   }

   public static void main(String[] args)  throws Exception{
     TestTimer timer = new TestTimer();
     timer.start();
   }
}
