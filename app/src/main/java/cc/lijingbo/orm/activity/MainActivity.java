package cc.lijingbo.orm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cc.lijingbo.leeorm.Orm;
import cc.lijingbo.orm.R;
import cc.lijingbo.orm.bean.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button create, update, add, delete, query;
    TextView tv;
    Orm mOrm;

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
                Student s1 = new Student();
                s1.setAge(18);
                s1.setEmail("haha@email.com");
                s1.setMale(true);
                s1.setClassName("Two Class");
                s1.setPhoneNumber(135678920);
                mOrm.save(s1);
                break;

            case R.id.query:
//                mOrm.quary();
                break;
            case R.id.update:
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
