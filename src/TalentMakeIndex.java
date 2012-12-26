
/**
 * <p>Title: 知识管理索引生成器</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 南方科能</p>
 * @author 毕元堂
 * @version 1.0
 */

import tornado.irms.se.index.* ;
import tornado.irms.util.log.*;
import tornado.irms.util.conf.*;
import tornado.irms.util.comm.*;



public class TalentMakeIndex implements IIndexerEvent{
  static LoggerManager m_logManager = new LoggerManager(TalentMakeIndex.class);
     private CommFunction m_commFunction = new CommFunction();

     TalentMakeIndex(){}

     private int m_Item ;
     private boolean m_bEndRecProc ;

     // Description: Indicates the progress changes.
     // Parameter: strText as the text of the current status.
     public void OnChangeMessage(String strText)
     {
         if( !m_bEndRecProc )
             System.out.println(m_Item) ;
         m_bEndRecProc = true ;

         System.out.println(strText) ;
     }

     // Description: Indicates the percentage of progress changes.
     // Parameter: nProgress  as the percentage of the current status (0~100).
     public void OnChangeProgress(int nProgress)
     {
         System.out.print(nProgress + "\r") ;
         if( nProgress == 100 )
             System.out.print("Done\n") ;
     }

     // Description: Errors occur.
     // Parameter: strErrMsg as the text error messages.
     public void OnError(String strErrMsg)
     {
         System.out.println("Error: " + strErrMsg) ;
     }

     // Description: The completion of the indexing task.
     public void OnFinish()
     {
         System.out.println("Finished...") ;
     }

     // Description: The processing of a document is completed.
     // Parameter: nItem  the document count in the index database.
     // Parameter: strDocKey  as the document key of this data item.
     public void OnRecordProcessed(int nItem, String strDocKey)
     {
         System.out.print(".") ;
         if( nItem % 50 == 0 )
             System.out.println(nItem) ;
         m_Item = nItem ;
     }

     private void Test(String strIniPath, String strDBName, boolean bMakeAuth)
     {
         ConfigManager manager = ConfigManager.GetInstance();
         IConfigSection section = manager.LoadConfig("INI", strIniPath);
         if (section == null)
         {
             System.out.println("Error: Get ConfigSection fail");
             return;
         }

         // log property
         String strLogProperty = section.GetValue("/Log/IndexerProp", null);
         m_logManager.setConfigure(strLogProperty);

         m_logManager.event(ILogSystem.LEVEL_INFO, "Test()", "0000", "MakeIndex Start", "DBName=" + strDBName);



         Indexer indexer=new Indexer() ;

         indexer.SetEventObject(this) ;

         if( indexer.Initialize(strIniPath, strDBName) )
         {
             indexer.EnableShellMessage(false) ;
             m_Item = 0 ;
             m_bEndRecProc = false ;

             if( bMakeAuth )
             {
                 System.out.println("Authority index flag is [ON].") ;
                 indexer.SetOption(1, 1) ;
             }

             // lock index
             // indexer.LockIndex(true) ;
             indexer.MakeIndex(null) ;
             // unlock index
             // indexer.LockIndex(false) ;

             System.out.println("Index time: " + indexer.IndexTime() + " sec") ;
             indexer.Terminate() ;
         }
         else
             System.out.println(indexer.GetErrorMessage()) ;

         m_logManager.event(ILogSystem.LEVEL_INFO, "Test()", "0000", "MakeIndex End", "DBName=" + strDBName);
     }

    public void exeIt(){
      String[] args = {"km"};
      if( args.length < 1 )
          {
              System.out.println("Usage: java MakeIndex [DB Name] [Make Security Index(0/1)]") ;
              return ;
          }
          else
          {
              boolean bMakeAuth=false ;
              if( args.length == 2 )
                  bMakeAuth = (Integer.parseInt(args[1]) == 1) ;
                System.out.println("---------------bMakeAuth="+bMakeAuth);

              try
              {
                  // test1
                  TalentMakeIndex  mt1=new TalentMakeIndex() ;

                  mt1.Test("./../../IRMS.ini", args[0], bMakeAuth) ;
                  System.out.println("") ;
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
          }

          System.gc() ;
      }


    public static void main(String[] args){
      TalentMakeIndex maker = new TalentMakeIndex();
      maker.exeIt();
    }


//*********************************************** Test Program
     public static void main_bk(String[] args)
     {
         if( args.length < 1 )
         {
             System.out.println("Usage: java MakeIndex [DB Name] [Make Security Index(0/1)]") ;
             return ;
         }
         else
         {
             boolean bMakeAuth=false ;
             if( args.length == 2 )
                 bMakeAuth = (Integer.parseInt(args[1]) == 1) ;

             try
             {
                 // test1
                 TalentMakeIndex  mt1=new TalentMakeIndex() ;

                 mt1.Test("./../../IRMS.ini", args[0], bMakeAuth) ;
                 System.out.println("") ;
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
         }

         System.gc() ;
     }


}
