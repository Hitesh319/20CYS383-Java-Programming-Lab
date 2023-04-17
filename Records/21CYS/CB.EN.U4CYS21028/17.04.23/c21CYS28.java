package com.amrita.hitesh;
/**
 * @author Hitesh manjunath K cys21028
 * This program is an intro to the classes and objects
 */
public class c21CYS28 {
    //declare variables
    static double assignGrade;
    static int rolll = 28;
    static String name = "Hitesh";
    //create a PublishResult() method
    public void publishResult(){
        System.out.println("Results are published");

    }
    // create a graded() method
        public void graded(double grade){
        System.out.println("Grade is :"+grade);
        }
        /** create a rolll() method
        * @param roll roll is assigned to student
         */
        public void rolll() {

            System.out.println("Roll no is : " + rolll);
        }
        /** create a name() method
        * @param name name assigned to student
        */public void name(){
        System.out.println("My name is "+name);
        }
         /** create a age() method
        * @param age age assigned to student
         */
         public void age(){
        int age = 19;
        System.out.println("my age is "+ age);
        }

    /**Modify the attribute and call the methods on myClass object
     * @param args this is main function
     */
    public static void main(String[] args) {
        c21CYS28 myClass = new c21CYS28();
        myClass.publishResult();

        myClass.assignGrade = 9.8;
        myClass.graded(assignGrade);
        myClass.rolll();
        myClass.name();
        myClass.age();
    }

}