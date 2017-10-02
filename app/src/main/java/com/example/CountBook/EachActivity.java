package com.example.CountBook;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * It will generate a new activities along with its properties.
 *
 * October 2, 2017
 * @author xinmeng1
 * @version  1.0
 * @since 1.0
 */

public class EachActivity {
    private String name;
    private String date;
    private int currentValue;
    private int initValue;
    private String comment;
    /**
     * generate format
     * @param name
     * @param currentValue
     * @param initValue
     * @param comment
     */
    public EachActivity(String name, int currentValue, int initValue, String comment) {
        super();
        this.name = name;
        this.date = setDate();
        this.currentValue=currentValue;
        this.initValue=initValue;
        this.comment = comment;
    }

    /**
     * set name
     * @param whatName
     */
    public void setName(String whatName){
        this.name=whatName;
    }

    /**
     * return name
     * @return name
     */
    public String getName(){
        return this.name;
    }


    /**
     * set date
     * @collebrate Guanfang Dong
     */
    public String setDate(){
        Date initDate= new Date();
        DateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        this.date=ft.format(initDate);
        return ft.format(initDate);
    }

    /**
     * currentValue plus 1
     */
    public void plus(){
        this.currentValue=this.currentValue+1;
    }



    /**
     * set currentValue to initValue
     */
    public void reset(){
        this.currentValue=this.initValue;
    }

    /**
     * currentValue minus 1
     */

    public void minus(){
        if (this.currentValue>0){
            this.currentValue=this.currentValue-1;}
    }

    /**
     * return count
     * @return count
     */
    public int getCurrentValue(){
        return this.currentValue;
    }


    /**
     * set count
     * @param count
     */

    public void setCount(int count){
        this.currentValue = count;
    }


    /**
     * set setComment
     * @param comment
     */
    public void setComment(String comment){
        this.comment=comment;
    }


    /**
     * return comment
     * @return comment
     */


    public String getComment(){
        return this.comment;
    }

    /**
     * setInitValue
     */

    public void setInitValue(int value){this.initValue=value;}
    @Override
    /**
     * @return string format
     */
    public String toString(){
        return "Name: "+name+"\n"+"Date: "+date+"\n"+"Count: "+currentValue;
    }
}
