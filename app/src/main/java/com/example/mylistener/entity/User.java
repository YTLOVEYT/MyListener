package com.example.mylistener.entity;

/**
 * MyListener
 * Created by YinTao on 2018/4/14.
 */
public class User
{
    private int id;
    private String name;
    private int age;
    private String sex;

    private User(Builder builder)
    {
        id = builder.id;
        name = builder.name;
        age = builder.age;
        sex = builder.sex;
    }

    public static final class Builder
    {
        private int id;
        private String name;
        private int age;
        private String sex;

        public Builder(int id, String name)
        {
            this.id = id;
            this.name = name;
        }

        public Builder age(int val)
        {
            age = val;
            return this;
        }

        public Builder sex(String val)
        {
            sex = val;
            return this;
        }

        public User build()
        {
            return new User(this);
        }
    }
}
