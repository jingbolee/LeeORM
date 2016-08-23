package cc.lijingbo.orm.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import cc.lijingbo.leeorm.Orm;
import cc.lijingbo.orm.R;
import cc.lijingbo.orm.bean.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button create, update, add, delete, query;
    TextView tv;
    Orm mOrm;

    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOrm = Orm.getInstance(this, null, 0);
        create = (Button) findViewById(R.id.create);
        update = (Button) findViewById(R.id.update);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        tv = (TextView) findViewById(R.id.tv);

        create.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create:
                Student s1;
                for (int i = 100; i < 1100; i++) {
                    s1 = new Student();
                    s1.setName("Jerome" + i);
                    s1.setAge(18 + i);
                    s1.setEmail("haha" + i + "@email.com");
                    s1.setMale(Math.round(Math.random()) == 1 ? true : false);
                    s1.setClassName("Two Class");
                    s1.setPhoneNumber(1356780 + i);
                    id = mOrm.save(s1);
                }
                break;

            case R.id.query:
                List<Student> students = new ArrayList<>();
                Class<Student> studentClass = Student.class;
                Cursor cursor = mOrm.queryAll(Student.class);
                Student student;
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        student = new Student();
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        if (name != null) {
                            student.setName(name);
                        }
                        int age = cursor.getInt(cursor.getColumnIndex("age"));
                        if (age != 0) {
                            student.setAge(age);
                        }
                        String className = cursor.getString(cursor.getColumnIndex("class_name"));
                        if (className != null) {
                            student.setClassName(className);
                        }
                        int isMale = cursor.getInt(cursor.getColumnIndex("is_male"));
                        if (isMale == 1) {
                            student.setMale(true);
                        } else {
                            student.setMale(false);
                        }
                        int phoneNumber = cursor.getInt(cursor.getColumnIndex("phone_number"));
                        if (phoneNumber != 0) {
                            student.setPhoneNumber(phoneNumber);
                        }
                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        if (email != null) {
                            student.setEmail(email);
                        }
                        KLog.e(student.toString());
                        students.add(student);
                    }
                    Toast.makeText(MainActivity.this,"查询完成",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update:
                Student s11 = new Student();
                s11.setName("Jerome102");
//                mOrm.update();
                break;
            case R.id.delete:
//                mOrm.delete();
                break;
            default:
                break;
        }
    }
}
