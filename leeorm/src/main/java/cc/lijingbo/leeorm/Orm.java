package cc.lijingbo.leeorm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.socks.library.KLog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cc.lijingbo.leeorm.annotation.Column;
import cc.lijingbo.leeorm.annotation.Table;

/**
 * @Author: Li Jingbo
 * @Date: 2016-08-23 09:29
 */
public class Orm {
    Context context;
    ORMSQLiteOpenHelper helper;
    String dbName = "LEEORM.db";
    int dbVersion = 1;
    static boolean isCreateTable;
    SQLiteDatabase database;

    static {
        isCreateTable = false;
        KLog.init(true, "Library");
    }

    private static Orm INSTANCE = null;

    public static Orm getINSTANCE(){
        if (INSTANCE == null) {
            synchronized (Orm.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Orm();
                }
            }
        }
        return INSTANCE;
    }

    public static Orm getINSTANCE(Context context, String dbName, int dbVersion) {
        if (INSTANCE == null) {
            synchronized (Orm.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Orm(context, dbName, dbVersion);
                }
            }
        }
        return INSTANCE;
    }

    private Orm(){
        helper = new ORMSQLiteOpenHelper(this.context, this.dbName, null, this.dbVersion);
    }


    private Orm(Context context, String dbName, int dbVersion) {
        this.context = context;
        if (dbName != null) {
            this.dbName = dbName;
        }
        if (dbVersion >= 1) {
            this.dbVersion = dbVersion;
        }
        helper = new ORMSQLiteOpenHelper(this.context, this.dbName, null, this.dbVersion);
    }

    private String createTable(Object object) {
        Class c = object.getClass();
        StringBuffer sb = new StringBuffer();
        boolean isExit = c.isAnnotationPresent(Table.class);
        if (!isExit) {
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value().toUpperCase();
        sb.append("create table ").append(tableName).append(" ( ").append("id integer primary key" +
                " autoincrement");
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            boolean fExit = field.isAnnotationPresent(Column.class);
            if (!fExit) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            sb.append(", ").append(columnName).append(" text");
        }
        sb.append(")");
        isCreateTable = true;
        return sb.toString();
    }

    public long save(Object object) {
        if (!isCreateTable) {
            String sql = createTable(object);
            if (sql != null) {
                helper.setSql(sql);
                if (database == null) {
                    database = helper.getReadableDatabase();
                }
            }
        }
        if (database == null) {
            database = helper.getReadableDatabase();
        }
        Class c = object.getClass();
        ContentValues values = new ContentValues();
        StringBuffer sb = new StringBuffer();
        boolean isExit = c.isAnnotationPresent(Table.class);
        if (!isExit) {
            return 0;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value().toUpperCase();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            boolean isFExit = field.isAnnotationPresent(Column.class);
            if (!isFExit) {
                continue;
            }
            Column column = field.getAnnotation(Column.class);
            String columnName = column.value();
            String fieldName = field.getName();
            StringBuffer fieldNameSb = new StringBuffer();
            if (fieldName.startsWith("is")) {
                fieldNameSb.append(fieldName);
            } else {
                fieldNameSb.append("get").append(fieldName.substring(0, 1).toUpperCase())
                        .append(fieldName.substring(1));
            }
            String getMethodName = fieldNameSb.toString();
            Object fieldValue = null;
            try {
                Method method = c.getMethod(getMethodName);
                fieldValue = method.invoke(object);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue instanceof String && (String) fieldValue != null) {
                values.put(columnName, (String) fieldValue);
            } else if (fieldValue instanceof Integer && (Integer) fieldValue != 0) {
                values.put(columnName, (Integer) fieldValue);
            } else if (fieldValue instanceof Boolean) {
                values.put(columnName, (Boolean) fieldValue);
            }
        }
        return database.insert(tableName, null, values);
    }

    public Cursor queryAll(Class c) {
        boolean isExit = c.isAnnotationPresent(Table.class);
        if (!isExit) {
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value().toUpperCase();
        if (database == null) {
            database = helper.getReadableDatabase();
        }
        return database.query(tableName, null, null, null, null, null, null);
    }

    public Cursor queryById(Class c, long id) {
        boolean isExit = c.isAnnotationPresent(Table.class);
        if (!isExit) {
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value().toUpperCase();
        if (database == null) {
            database = helper.getReadableDatabase();
        }
        return database.query(tableName, null, "id=?", new String[]{String.valueOf(id)}, null, null, null);
    }

    public void update(Object object) {
        Class c= object.getClass();
        boolean isExit = c.isAnnotationPresent(Table.class);
        if (!isExit) {
            return;
        }
        ContentValues values = new ContentValues();
        if (database == null) {
            database = helper.getReadableDatabase();
        }
        Table table = (Table) c.getAnnotation(c);
        String tableName = table.value().toUpperCase();




//        database.update(tableName,values,)
    }

    public void delete(Class c) {

    }
}
