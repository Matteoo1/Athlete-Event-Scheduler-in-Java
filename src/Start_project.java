/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.Start_project;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Start_project {

    public static void main(String[] args)  throws IOException
    {
//        System.out.println("Hello World!");
        Scanner ord=new Scanner(System.in);
        Scanner ord2=new Scanner(System.in);
        int i,j,time1,sum_time=0;
        String str="",order2,order;
        boolean test=true,test2=false;
        Analysis_CSV f_csv=new Analysis_CSV();
        Athletes_schedule obj_table=new Athletes_schedule(); 
        
        Analysis_CSV obj_csv3=new Analysis_CSV();
        Best_Athlete[] bst1;
        Best_Athlete bst2[],bst3[];
        bst1=new Best_Athlete[6];
        bst2=new Best_Athlete[6];
        bst3=new Best_Athlete[6];
        File file;
        
        int delay_time[][]=new int[14][9];
        for(i=0;i<14;i++)
            delay_time[i]=new int[9];
        
        for(i=0;i<14;i++)
            for(j=0;j<9;j++)
                delay_time[i][j]=0;
        
                
        while(test)
        {
            here:
            print("1- Enter the athletes file name.\n");
            if(test2==true)
            {
                print("2- Enter the team name to print the time table for it.\n");
                print("3- Contest winners names\n");
                print("4- Set delay time.\n");
                print("5- Print Delay time.\n");
                print("6- Exit.\n");
            }
            
            
            
            print("\nEnter the order number: ");
            order=ord.nextLine();
            
            if(order.equals("1")) 
            {
                //-------------------------------------------------------------------------------------
                //To find a time table
                test2=true;
                print("1- Enter file name: ");
                str=ord.nextLine();       //File name
                
                f_csv.csv_analysis(str);   //To sort the given file by age and gender
                obj_table.final_table("table.csv",delay_time);         //To find the time table for all our teams
                f_csv.sort_groups_and_games("Gamer_time\\","tables\\format_table.csv");  // To find the time table for each team individually
                print("Table created.\n\n");
                //-------------------------------------------------------------------------------------
                //To find the final table
                file = new File("files_analysis_final");
                file.mkdirs();
                obj_csv3.appendStrToFile("files_analysis_final\\Final_Running.csv", "",false);
                obj_csv3.appendStrToFile("files_analysis_final\\Final_jumping.csv", "",false);
                obj_csv3.appendStrToFile("files_analysis_final\\Final_throwing.csv", "",false);
                bst1=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_Running.csv",0,5,6,false);    //Running
                bst2=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_jumping.csv",6,9,4,true);    //jumping
                bst3=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_throwing.csv",10,10,1,true);    //throwing
                
                obj_table.final_table_final("table.csv");
            }
            else if(order.equals("2") && test2==true)
            {
                obj_table.gamer_find();   // To request theteam's name  to print its  time table 
            }
            else if(order.equals("3") && test2==true)
            {
                
                print("Running:\n");
                print("Name: "+bst1[0].name+" "+bst1[0].surname+"\n");
                print("Age: "+bst1[0].age+"   ,   Sex: "+bst1[0].sex+"\n");
                print("Total seconds: "+bst1[0].avg+"\n");
//                    for(i=0;i<bst1.length;i++)
//                        print("name: "+bst1[i].name+"   ,   avg: "+bst1[i].avg+"\n");
                print("==========================================================\n");
                print("Jumping:\n");
                print("Name: "+bst2[0].name+" "+bst2[0].surname+"\n");
                print("Age: "+bst2[0].age+"   ,   Sex: "+bst2[0].sex+"\n");
                print("Total meters: "+bst2[0].avg+"\n");
//                    for(i=0;i<bst2.length;i++)
//                        print("name: "+bst2[i].name+"   ,   avg: "+bst2[i].avg+"\n");
                print("==========================================================\n");
                print("Throwing:\n");
                print("Name: "+bst3[0].name+" "+bst3[0].surname+"\n");
                print("Age: "+bst3[0].age+"   ,   Sex: "+bst3[0].sex+"\n");
                print("Total meters: "+bst3[0].avg+"\n");
//                    for(i=0;i<bst3.length;i++)
//                        print("name: "+bst3[i].name+"   ,   avg: "+bst3[i].avg+"\n");
                print("\n");
                
                
            }
            else if(order.equals("4"))
            {
                i=print_athletes();
                j=print_station();
                time1=print_input_int("Enter the delay time value: ");
                sum_time+=time1;
                delay_time[i][j]=time1;
                //============================================================================
                
                //To find a time table
//                test2=true;
//                print("1- Enter file name: ");
//                str=ord.nextLine();
                
                f_csv.csv_analysis(str);   //To sort the given file by age and gender
                obj_table.final_table("table.csv",delay_time);         //To find the time table for all our teams
                f_csv.sort_groups_and_games("Gamer_time\\","tables\\format_table.csv");  // To find the time table for each team individually
                print("Table created.\n\n");
                //-------------------------------------------------------------------------------------
                //To find the final schedual 
                file = new File("files_analysis_final");
                file.mkdirs();
                obj_csv3.appendStrToFile("files_analysis_final\\Final_Running.csv", "",false);
                obj_csv3.appendStrToFile("files_analysis_final\\Final_jumping.csv", "",false);
                obj_csv3.appendStrToFile("files_analysis_final\\Final_throwing.csv", "",false);
                bst1=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_Running.csv",0,5,6,false);    //Running
                bst2=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_jumping.csv",6,9,4,true);    //jumping
                bst3=obj_csv3.array_bestGamer(str,"files_analysis_final\\Final_throwing.csv",10,10,1,true);    //jumping
                
                obj_table.final_table_final("table.csv");
                
            }
            else if(order.equals("5"))
            {
                print("Delay time = "+sum_time+"\n");
            }
            else if(order.equals("6"))
            {
                test=false;
            }
            else
            {
                print("\nError: Try again.\n");
            }

        }
        

                
        
        
    }
    
    static void print(String str)
    {
        System.out.print(str);
    }
    static int print_input_int(String str)
    {
        int num;
        Scanner sc = new Scanner(System.in);
        print(str);
        num=sc.nextInt();
        return num;
    }
    static int print_athletes()
    {
        int i;
        Scanner sc = new Scanner(System.in);
        Athletes_schedule x = new Athletes_schedule();
        for(i=0;i<x.groups.length;i++)
            print((i+1)+"- "+x.groups[i]+"\n");
        
        print("Enter the team number: ");
        i=sc.nextInt();
        i=i-1;
        return i;
    }
    
    static int print_station()
    {
        int j;
        Scanner sc = new Scanner(System.in);
        Athletes_schedule x = new Athletes_schedule();
        for(j=0;j<x.sections.length;j++)
            print((j+1)+"- "+x.sections[j]+"\n");
        
        print("Enter the station number: ");
        j=sc.nextInt();
        j=j-1;
        return j;
    }
}
