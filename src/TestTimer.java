
/**
 * <p>Title: ֪ʶ���������Ķ�ʱ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: �Ϸ�����</p>
 * @author ��Ԫ��
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
       },10*1000,1000*60*60);//��ʱ������10���,ÿ��һСʱ������һ��run();
   }

   public static void main(String[] args)  throws Exception{
     TestTimer timer = new TestTimer();
     timer.start();
   }
}
