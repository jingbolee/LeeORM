package cc.lijingbo.orm.bean;

import cc.lijingbo.leeorm.annotation.Column;
import cc.lijingbo.leeorm.annotation.Table;

/**
 * @Author: Li Jingbo
 * @Date: 2016-08-23 09:37
 */
@Table("student")
public class Student {

//    @Column("id")
//    private int id;
    @Column("name")
    private String name;
    @Column("age")
    private int age;
    @Column("class_name")
    private String className;
    @Column("is_male")
    private boolean isMale;
    @Column("phone_number")
    private int phoneNumber;
    @Column("email")
    private String email;

//    public int getId() {
//        return id;
//    }

//    public void setId(int id) {
//        this.id = id;
////    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.trim().length() > 0) {
            this.name = name.trim().toLowerCase();
        }

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0 || age <= 100) {
            this.age = age;
        }

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
