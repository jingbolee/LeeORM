package cc.lijingbo.leeorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.socks.library.KLog;

/**
 * @Author: Li Jingbo
 * @Date: 2016-08-23 09:11
 */
public class ORMSQLiteOpenHelper extends SQLiteOpenHelper {

    String sql;
    public ORMSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version ,String sql) {
        super(context, name, factory, version);
        this.sql = sql;
        KLog.e("创建表的 sql 语句为："+sql);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
        KLog.e("数据库创建成功了...");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
