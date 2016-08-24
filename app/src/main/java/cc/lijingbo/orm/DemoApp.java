package cc.lijingbo.orm;

import android.app.Application;

import com.socks.library.KLog;

import cc.lijingbo.leeorm.Orm;

/**
 * @Author: Li Jingbo
 * @Date: 2016-08-23 09:32
 */
public class DemoApp extends Application {
    private static final String TAG = "DemoApp";
    Orm mOrm;
    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(cc.lijingbo.orm.BuildConfig.LOG_DEBUG, "DemoApp");
        mOrm = Orm.getINSTANCE(this,null,0);
    }
}
