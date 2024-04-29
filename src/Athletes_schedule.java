/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Start_project;

import java.io.*;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author ASUS
 */
public class Athletes_schedule 
{
    int k=0;
    String sections[]=new String[]{"Sprint(60-200)","Running Circal(800-1600-3000-60)","Jumping long/triple I","Jumping long/triple II"
                        ,"Jumping High I","Jumping High II","Jumping Pole","Throwing Shot I","Throwing Shot II"};
    String groups[]=new String[]{"7_8_F","7_8_M","9_10_F","9_10_M","11_12_F","11_12_M"
        ,"13_14_F","13_14_M","15_16_F","15_16_M","17_18_F","17_18_M","18_large_F","18_large_M"};
    String days[]=new String[]{"Mon","Tues","Wed","Thur","Fri"};
    int day_i=0;
    String groups_final[]=new String[]{"Final_Running","Final_jumping","Final_throwing"};
    
    //**************************************************************************************************
    void final_table_final(String file_name) throws IOException
    {
        int j;
        Athletes_schedule table1=new Athletes_schedule();
        table1.day_i=day_i;
        int n,m,hour1;
        n=3; m=9; hour1=600;
        int all_t1[][]=new int[n][m];
        int all_g1[][]=new int[n][m];
        int g_time_copy[]=new int[m];
        Analysis_CSV obj_csv=new Analysis_CSV();
        Analysis_CSV f_csv=new Analysis_CSV();
        String file_name2=file_name;
        
        all_t1=f_csv.analysis_all_games_final();                      //array time for each game
        all_g1=f_csv.analysis_parts(all_t1);                    //array of athletes in a game
        
//        File file = new File("tables");
//        file.mkdirs();
        obj_csv.appendStrToFile("tables\\format_"+file_name,"",false);
        file_name="tables\\"+file_name;
//        obj_csv.appendStrToFile(file_name,"",false);
        while(table1.check_all_games(all_g1,n,m))
        {
            table1.days_write(file_name);     //Write day's name
            for(j=0;j<m;j++)
            g_time_copy[j]=0;       //The start time for all games is from minute zero
            //---------------------hour_final--------------------------------
            table1.games_table_final(file_name,n,m,hour1,all_t1,all_g1,g_time_copy); table1.k=0;
            
        }
        //----------------Find when all teams finish playing-------------------
        int max1=g_time_copy[0];
        String start1_time,end1_time;
        Time_data obj_time=new Time_data();
        for(j=0;j<m;j++)
            if(g_time_copy[j]>max1)
                max1=g_time_copy[j];
        //-----------------Award ceremony------------------
        start1_time=obj_time.time_format(8,max1+5+30);
        end1_time=obj_time.time_format(8,(max1+65));
        obj_csv.appendStrToFile(file_name,"\nAwards ceremony ("+start1_time+"-"+end1_time+")\n", true);
        //----------------Find when all teams finish playing-------------------

        
//        day_i=table1.day_i;
        day_i=0;
        table1.games_table_format(file_name2);    //Delete the blank lines in the table
    }
    
    void games_table_final(String file_name,int n,int m,int hour_final,int all_time[][],int all_games[][],int g_time_copy[]) throws IOException
    {
        Time_data obj_time=new Time_data();
        String start1_time,end1_time,start1_time_break,end1_time_break;
        int d;
        int tik[]=new int[14]; for(d=0;d<14;d++) tik[d]=0;
        Analysis_CSV obj_csv=new Analysis_CSV();
        int i,j,test1=0,h,count=0;
//        n=4;
//        m=3;
        Athlete f[]=new Athlete[n];  for(i=0;i<n;i++) f[i]=new Athlete(m);
        Station g[]=new Station[m];    for(i=0;i<m;i++) g[i]=new Station();
        for(i=0;i<m;i++)
            g[i].t2=g_time_copy[i];
        //for(i=0;i<m;i++) g[i].time=all_time[i];
        for(i=0;i<n;i++)
            for(j=0;j<m;j++)
                f[i].check_t[j]=all_time[i][j];
        
        for(i=0;i<n;i++)
            for(j=0;j<m;j++)
                f[i].check_g[j]=all_games[i][j];
        for(i=0;i<m;i++)
            g[i].break_time=5;     //Break time for each game is 1 minute
//        g[0].time=1;
//        g[1].time=2;
//        g[2].time=1;
//        hour_final=3;
        // f[0].g[0]=1;

        i=0; j=0;
        while(count<=n*m*3)
        {
            if(f[i].check_g[j]==0)
            //----------------------------------------------------------------------------
            {
                count=0;
                h=(f[i].t2>g[j].t2)? f[i].t2 :g[j].t2 ;
                h+=f[i].check_t[j];
                h+=5;
                if(h<=hour_final)
                {
                    tik[i]=1;
                    test1=0;
                    {
                        f[i].t1=(f[i].t2>g[j].t2)? f[i].t2 :g[j].t2;
                        f[i].t2=f[i].t1+f[i].check_t[j];
                        g[j].t2=f[i].t2;
                        //--------Break--------
                        f[i].s1=f[i].t2;
                        f[i].s2=f[i].t2 + g[j].break_time;
                        f[i].t2=f[i].s2;
                        //--------Break--------
                        
                        
                        //------------Format time to hours------------------
                        start1_time=obj_time.time_format(8,f[i].t1);
                        end1_time=obj_time.time_format(8,f[i].s1);
                        start1_time_break=obj_time.time_format(8,f[i].s1);
                        end1_time_break=obj_time.time_format(8,f[i].s2);
                        //------------Format time to hours------------------
//                        System.err.print(h+"f"+(i+1)+"("+start1_time+"-"+end1_time+"){"+(j+1)+"}("+start1_time_break+"-"+end1_time_break+") , ");
                        obj_csv.appendStrToFile(file_name,groups_final[i]+"("+start1_time+"-"+end1_time+")"+"("+start1_time_break+"-"+end1_time_break+"),",true);
//                        obj_csv.appendStrToFile(file_name,groups[i]+"("+f[i].t1+"-"+f[i].s1+")"+"("+start1_time_break+"-"+end1_time_break+") , ",true);
//                        System.err.print(h+"f"+(i+1)+"("+f[i].t1+","+f[i].t2+"){"+(j+1)+"} , ");
//                        obj_csv.appendStrToFile(file_name,"f"+(i+1)+"("+f[i].t1+"-"+f[i].t2+"){"+(j+1)+"},",true);
                        line(file_name,m,tik);
                        f[i].check_g[j]=1;
                    }
                    j++;
                    if(j==m) j=0;
                    i++;
                    if(i==n) i=0; 
                }
                else
                {
                    //cout<<"XXXXXXXX , ";
//                    System.err.print("XXXXXXXX1 , ");
                    obj_csv.appendStrToFile(file_name,",",true);
                    line(file_name,m,tik);
                    test1++;
                    if(test1==m*2) break;
                    j++;
                    if(j==m) j=0;
                }
            }
            //----------------------------------------------------------------------------
            else
            {
                count++; //if(count==n*m) break;
                //cout<<"test1="<<test1<<endl;
                i++;
//                System.err.print("*");
                if(i==n) 
                {
//                System.err.print("XXXXXXXX2 , ");
                obj_csv.appendStrToFile(file_name,",",true);
                line(file_name,m,tik);
                i=0;
                // cout<<"i==n"<<endl;
                j++;
                if(j==m) j=0;
                }
                else
                {
                    int x;
                    for(x=0;x<3;x++)
                    if(f[x].check_g[j]==0 && tik[x]==0)
                    {i=x; break;}
                    
                }
                
            }
        }
        for(i=0;i<n;i++)
            for(j=0;j<m;j++)
                all_games[i][j]=f[i].check_g[j];
        for(i=0;i<m;i++)
            g_time_copy[i]=g[i].t2;
        obj_csv.appendStrToFile(file_name,"\n",true);
    }
    
    //**************************************************************************************************
    
    //=================================Sort Group and Games=============================================
    void gamer_find() throws IOException      // To request the name of the team to print its  time table
    {
        int i;
        boolean test=false;
        String teamName;
        Scanner scr=new Scanner(System.in);
        System.out.print("Enter the team name: ");
        teamName=scr.nextLine();
        System.out.println("Name: "+teamName);
        for(i=0;i<14;i++)
            if(groups[i].equals(teamName))
            {
                test=true;
                break;
            }
        
        if(test==true)
        {
            System.out.println("\n---------------------------------------- "+teamName+" ----------------------------------------");
            gamer_find2("Gamer_time\\",teamName+".csv");
            System.out.println("\n---------------------------------------- "+teamName+" ----------------------------------------");
        }
        else
            System.out.println("Error : Try again.");
        
    }
    
    void gamer_find2(String path,String teamName) throws IOException
    {
        String str;
        BufferedReader f11=new BufferedReader(new FileReader(path+teamName));
        
        while((str = f11.readLine()) != null)
        {
            System.out.println(str);
        }
    }
    //=================================Sort Group and Games=============================================
    void final_table(String file_name,int delay_time[][]) throws IOException
    {
        int j;
        Athletes_schedule table1=new Athletes_schedule();
        table1.day_i=day_i;

        int n,m,hour1,lunch,coffe_break;
        n=14; m=9; hour1=600;
        int all_t1[][]=new int[n][m];
        int all_g1[][]=new int[n][m];
        int g_time_copy[]=new int[m];
        Analysis_CSV obj_csv=new Analysis_CSV();
        Analysis_CSV f_csv=new Analysis_CSV();
        String file_name2=file_name;
        
        //time[]-->3*m1    (Power Point)
        all_t1=f_csv.analysis_all_games();                      //Time array for each game
        //Join[]-->3*m1    (Power Point)
        all_g1=f_csv.analysis_parts(all_t1);                    //array of athletes in a game
        
        lunch=240;
        coffe_break=120;
        File file = new File("tables");
        file.mkdirs();
        obj_csv.appendStrToFile("tables\\format_"+file_name,"",false);
        file_name="tables\\"+file_name;
        obj_csv.appendStrToFile(file_name,"",false);
        while(table1.check_all_games(all_g1,n,m))
        {
            table1.days_write(file_name);
            for(j=0;j<m;j++)
            g_time_copy[j]=0;       //Starting time for all places from zero minutes
            //---------------------coffe_break--------------------------------
            table1.games_table(file_name,n,m,coffe_break,all_t1,all_g1,g_time_copy,delay_time); table1.k=0;
            obj_csv.appendStrToFile(file_name,"coffe break from 10:00 to 10:15,\n",true);
            for(j=0;j<m;j++)
            g_time_copy[j]=135;      //Start time for all games starting from the minute 135
//            System.out.println("\n222--------------------------------------------------");
//            table1.games_table("ccc.csv",n,m,420,all_t1,all_g1,g_time_copy); table1.k=0;
            //---------------------coffe_break--------------------------------
            table1.games_table(file_name,n,m,lunch,all_t1,all_g1,g_time_copy,delay_time); table1.k=0;
            obj_csv.appendStrToFile(file_name,"lunch from 12:00 to 13:00,\n",true);
            for(j=0;j<m;j++)
            g_time_copy[j]=300;        //Start time for all games starting from the minute
            table1.games_table(file_name,n,m,hour1,all_t1,all_g1,g_time_copy,delay_time); table1.k=0;
//            System.out.println("\n--------------------------------------------------");
        }
        day_i=table1.day_i;
        table1.games_table_format(file_name2);    //Delete the blank lines in the table
    }
    
    void days_write(String file_name) throws IOException
    {
        int i;
        Analysis_CSV obj_csv=new Analysis_CSV();
        if(day_i==5) day_i=0;
//        System.err.print(days[day_i]+"\n");
        obj_csv.appendStrToFile(file_name,days[day_i]+"\n",true);
        for(i=0;i<9;i++)
            obj_csv.appendStrToFile(file_name,sections[i]+",",true);
        obj_csv.appendStrToFile(file_name,"\n",true);
        day_i++;
    }
    
    boolean check_coma(String str,int m)
    {
        int i; String aux;
        Analysis_CSV obj_csv=new Analysis_CSV();
        for(i=0;i<m;i++)
            if((aux=obj_csv.getName_semicolon(str,i,',')).length()!=0)
                return true;
        return false;
    }
    
    void games_table_format(String file_name) throws IOException
    {
        int n;
        String str;
        Analysis_CSV obj_csv=new Analysis_CSV();
        BufferedReader file1=new BufferedReader(new FileReader("tables\\"+file_name));
        for(n=0;((str = file1.readLine()) != null);n++)  //To find the number of athletes inside the file
        {
            if(check_coma(str,9))
            obj_csv.appendStrToFile("tables\\format_"+file_name,str+"\n",true);
        }
        file1.close();
    }
    
    boolean find_f(int all_games[][],int n1,int n2,int j)
    {
        int i;
        for(i=n1;i<=n2;i++)
            if(all_games[i][j]==0)
                return true;
        return false;
    }
    
    void games_table(String file_name,int n,int m,int hour_final,int all_time[][],int all_games[][],int g_time_copy[],int delay_time[][]) throws IOException
    {
        Time_data obj_time=new Time_data();
        String start1_time,end1_time,start1_time_break,end1_time_break;
        int d;
        int tik[]=new int[14]; for(d=0;d<14;d++) tik[d]=0;
        Analysis_CSV obj_csv=new Analysis_CSV();
        int i,j,test1=0,h,count=0;
//        n=4;
//        m=3;
        Athlete f[]=new Athlete[n];  for(i=0;i<n;i++) f[i]=new Athlete(m);
        Station g[]=new Station[m];    for(i=0;i<m;i++) g[i]=new Station();
        for(i=0;i<m;i++)               //Stations start time
            g[i].t2=g_time_copy[i];
        //for(i=0;i<m;i++) g[i].time=all_time[i];
        
        for(i=0;i<n;i++)              //Array of time spent for each team in each location
            for(j=0;j<m;j++)
                f[i].check_t[j]=all_time[i][j];
        
        for(i=0;i<n;i++)             //Subscription array for each team place
            for(j=0;j<m;j++)
                f[i].check_g[j]=all_games[i][j];
        for(i=0;i<m;i++)
            g[i].break_time=5;     //Break time for each game is 1 minute
//        g[0].time=1;
//        g[1].time=2;
//        g[2].time=1;
//        hour_final=3;
        // f[0].g[0]=1;

        i=0; j=0;
        while(count<=n*m*3)
        {
            if(f[i].check_g[j]==0)
            //----------------------------------------------------------------------------
            {
                count=0;
                h=(f[i].t2>g[j].t2)? f[i].t2 :g[j].t2 ;
                h+=f[i].check_t[j];
                h+=g[j].break_time;       //Break 
                h+=delay_time[i][j];      //Dealy time
                if(h<=hour_final)
                {
                    tik[i]=1;
                    test1=0;
                    {
                        f[i].t1=(f[i].t2>g[j].t2)? f[i].t2 :g[j].t2;
                        f[i].t1+=delay_time[i][j];     //Dealy time
//                        System.out.println(i+","+j+"= "+delay_time[i][j]+"\n");
                        f[i].t2=f[i].t1+f[i].check_t[j];
                        g[j].t2=f[i].t2;
                        //--------Break--------
                        f[i].s1=f[i].t2;
                        f[i].s2=f[i].t2 + g[j].break_time;
                        f[i].t2=f[i].s2;
                        //--------Break--------
                        
                        
                        //------------Mange time to be in hours------------------
                        start1_time=obj_time.time_format(8,f[i].t1);
                        end1_time=obj_time.time_format(8,f[i].s1);
                        start1_time_break=obj_time.time_format(8,f[i].s1);
                        end1_time_break=obj_time.time_format(8,f[i].s2);
                        //------------Mange time to be in hours------------------
//                        System.err.print(h+"f"+(i+1)+"("+start1_time+"-"+end1_time+"){"+(j+1)+"}("+start1_time_break+"-"+end1_time_break+") , ");
                        obj_csv.appendStrToFile(file_name,groups[i]+"("+start1_time+"-"+end1_time+")"+"("+start1_time_break+"-"+end1_time_break+"),",true);
//                        obj_csv.appendStrToFile(file_name,groups[i]+"("+f[i].t1+"-"+f[i].s1+")"+"("+start1_time_break+"-"+end1_time_break+") , ",true);
//                        System.err.print(h+"f"+(i+1)+"("+f[i].t1+","+f[i].t2+"){"+(j+1)+"} , ");
//                        obj_csv.appendStrToFile(file_name,"f"+(i+1)+"("+f[i].t1+"-"+f[i].t2+"){"+(j+1)+"},",true);
                        line(file_name,m,tik);
                        f[i].check_g[j]=1;
                    }
                    j++;
                    if(j==m) j=0;
                    i++;
                    if(i==n) i=0; 
                }
                else
                {
                    //cout<<"XXXXXXXX , ";
//                    System.err.print("XXXXXXXX1 , ");
                    obj_csv.appendStrToFile(file_name,",",true);   //make a blank block
                    line(file_name,m,tik);
                    test1++;
                    if(test1==m*2) break;
                    j++;
                    if(j==m) j=0;
                }
            }
            //----------------------------------------------------------------------------
            else
            {
                count++; //if(count==n*m) break;
                //cout<<"test1="<<test1<<endl;
                i++;
//                System.err.print("*");
                if(i==n) 
                {
//                System.err.print("XXXXXXXX2 , ");
                obj_csv.appendStrToFile(file_name,",",true);
                line(file_name,m,tik);
                i=0;
                // cout<<"i==n"<<endl;
                j++;
                if(j==m) j=0;
                }
                else
                {
                    //Search for teams that are involved in the same game and do not have a tik sign, meaning they did not play the same lineر
                    int x;
                    for(x=0;x<14;x++)
                    if(f[x].check_g[j]==0 && tik[x]==0)      //Subscribers to the game and not made for them tik
                    {i=x; break;}
                    
                }
                
            }
        }
        
        //Transfer the subscription array for athletes to could  complete the next day with the rest of the athletes
        for(i=0;i<n;i++)
            for(j=0;j<m;j++)
                all_games[i][j]=f[i].check_g[j];       
        
        //Move the start time array to places in order to add lunch or coffee time
        for(i=0;i<m;i++)
            g_time_copy[i]=g[i].t2;
        
        //When the algorithm is finished, we move to a new line
        obj_csv.appendStrToFile(file_name,"\n",true);
    }
    
    boolean check_all_games(int f[][],int n,int m)
    {
        int i,j;
        for(i=0;i<n;i++)
            for(j=0;j<m;j++)
                if(f[i][j]==0)
                    return true;
        return false;
    }
    
    void line(String file_name,int m,int tik[]) throws IOException
    {
        Analysis_CSV obj_csv=new Analysis_CSV();
        k++;
        if(k==m) 
        {
            for(int d=0;d<14;d++) tik[d]=0;
//            System.err.println("");
            obj_csv.appendStrToFile(file_name,"\n",true);
            k=0;
        }
    }
    
}
