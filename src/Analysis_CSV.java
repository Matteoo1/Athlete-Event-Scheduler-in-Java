/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Start_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class Analysis_CSV 
{
    String sections[]=new String[]{"Sprint(60-200)","Running Circal(800-1600-3000-60)","Jumping long/triple I","Jumping long/triple II"
                        ,"Jumping High I","Jumping High II","Jumping Pole","Throwing Shot I","Throwing Shot II"};
    String groups[]=new String[]{"7_8_F","7_8_M","9_10_F","9_10_M","11_12_F","11_12_M"
        ,"13_14_F","13_14_M","15_16_F","15_16_M","17_18_F","17_18_M","18_large_F","18_large_M"};
    String days[]=new String[]{"Mon","Tues","Wed","Thur","Fri"};
    
    
    //================================= Best Gamer =============================================
    //the explanation
    //The results of the game start from the start to the end and you must achieve six actual results, which is the variable count
    //As for the type of sort, descending means true, and ascending means false
    //As for the file name here, we give it the athletes information file
    
    //To find and store the information of the first six athletes from a set of games and write the information of the six athletes into a file
    //This function returns the array of the six athletes participating in a set of games
    Best_Athlete[] array_bestGamer(String fileName,String fileName_destination,int start,int end,int count,boolean sort_type) throws IOException
    {
        int i,k=0;
        String str;
        
        //----------------------------------------------------------------------
        BufferedReader f11=new BufferedReader(new FileReader(fileName));

        while((str = f11.readLine()) != null)    //Find the gamers that will play on games
        {
            if(check_gamer_in_game(str, start, end, count))
                k++;
        }
        f11.close();
        //----------------------------------------------------------------------
        //Registering the participants' information that one of them can take the prize
        f11=new BufferedReader(new FileReader(fileName)); 
        Best_Athlete bst_g[]=new Best_Athlete[k];     // A array of the number of subscribers to record their information in
        for(i=0;i<k;i++)
            bst_g[i]=new Best_Athlete();
        
//        i=0;
        k=0;
        while((str = f11.readLine()) != null)
        {
            if(check_gamer_in_game(str, start, end, count))
            {
                bst_g[k].name=getName_semicolon(str, 1, ',');
                bst_g[k].surname=getName_semicolon(str, 2, ',');
                bst_g[k].sex=getName_semicolon(str, 3, ',');
                bst_g[k].age=getName_semicolon(str, 4, ',');
                
                for(i=0;i<11;i++)
                    if(i>=start && i<=end)
                    {
                        bst_g[k].values[i]=getName_semicolon(str,i+5,',');
                    }
                    else
                    {
                        bst_g[k].values[i]="0";
                    }
            
                bst_g[k].avg=0;           //Finding the number of seconds or meters in a set of games
                for(i=start;i<=end;i++)
                bst_g[k].avg+=find_mTime(getName_semicolon(str,i+5, ','));
//                bst_g[k].avg=bst_g[k].avg/6;
                k++;
            }
        }
        f11.close();
        //----------------------------------------------------------------------
        bubblesort_bestGamer(bst_g, sort_type);         //Arrange the array elements in ascending or descending order by the score
        //----------------------------------------------------------------------
        //After sort we choce first 6 to lote them share in final
        Best_Athlete final_running[]=new Best_Athlete[6];
        k=0;
        for(i=0;i<bst_g.length;i++)                //find first 6
            if(bst_g[i].name!="null")              //isolate the element that hase null value 
            {
                final_running[k++]=bst_g[i];
                if(k==6)
                    break;
            }
        //----------------------------------------------------------------------
        // Write the sixth gamers info  inside this file
        //fileName_destination
        
        int j;
        for(i=0;i<final_running.length;i++)
            {
                appendStrToFile(fileName_destination,final_running[i].name+",",true);
                appendStrToFile(fileName_destination,final_running[i].surname+",",true);
                appendStrToFile(fileName_destination,final_running[i].sex+",",true);
                appendStrToFile(fileName_destination,final_running[i].age+",",true);
                for(j=0;j<11;j++)
                    if(j>=start && j<=end)
                    {
                        appendStrToFile(fileName_destination,final_running[i].values[j]+",",true);
                    }
                    else
                    {
                        appendStrToFile(fileName_destination,",",true);
                    }
                appendStrToFile(fileName_destination,"\n",true);
                
            }
        
//        appendStrToFile(fileName_destination, str, sort_type);
        
        return final_running;
    }
    
    void bubblesort_bestGamer(Best_Athlete bst[],boolean sort_type)    //If  it is right the sort wel be descending elese acending
    {
        int i,j,n;
        Best_Athlete t=new Best_Athlete();
        n=bst.length;
        for(i=0;i<n-1;i++)
            for(j=0;j<n-1;j++)
                if(sort_type==false)    // acending
                {
                    if(bst[j].avg>bst[j+1].avg)
                    {
                        t=bst[j];
                        bst[j]=bst[j+1];
                        bst[j+1]=t;
                    }
                }
                else      // descending
                {
                    if(bst[j].avg<bst[j+1].avg)
                    {
                        t=bst[j];
                        bst[j]=bst[j+1];
                        bst[j+1]=t;
                    }
                }
    }
    
    double find_mTime(String str)    // Invert the time to secound 
    {
        int i;
        double result;
        String aux="";
        int min=0,second=0,msecond=0;
        
        for(i=0;i<str.length();i++)
        {
            if(str.charAt(i)!=':')
            aux+=str.charAt(i);
            if(str.charAt(i)==':')
            {
                min=Atoi(aux);
                aux="";
            }
        }
        double r=Atof(aux);
        result=min*60+r;
        
        
        return result;
    }
    
    boolean check_gamer_in_game(String str,int n1,int n2,int count)    //To test the player's participation in a game in order to be registered in the list of the best players
    {
        int i,j,k;
        
        k=0;
        for(i=n1;i<=n2;i++)
        if(getName_semicolon(str, i+5, ',').length()!=0)
            k++;
        
        if(k==count)
            return true;
        return false;
    }
    
    //***********************************************************************************************
    int[][] analysis_all_games_final() throws IOException         //final*******
    {
        int i,j;
        int all[][]=new int[14][9];
        String str_name[]=new String[]{"Final_Running.csv","Final_jumping.csv","Final_throwing.csv"};
        int join1[][];
        
        for(i=0;i<3;i++)
        {
            join1=analysis_parts("files_analysis_final\\"+str_name[i]);  //To find the subscription array for a game for a file
            all[i]=analysis_parts_totalTime(join1);         //To find the time array that contains the estimated time for games at each location or station
        }
        
        return all;
    }
    //***********************************************************************************************
    //================================= Best Gamer =============================================
    
    //=================================Sort GrouSortp and Games=============================================
    void sort_groups_and_games(String path,String fileName) throws IOException  //To sort the time table into several tables, where each table has the games time for a team
    {
        int i,j;
        String str,aux1,aux2,aux3;
//        String a[][]=new String[14][9];
//        for(i=0;i<14;i++) a[i]=new String[9];
        
        BufferedReader f11=new BufferedReader(new FileReader(fileName));
        
//        String path="Gamer_time";
        csv_create_files(path);
        File file = new File(path);
        file.mkdirs();
        
        while((str = f11.readLine()) != null)
        {
//            System.out.println("");
            for(j=0;j<9;j++)
            {
                aux1=getName_semicolon(str, j, ',');
                if(set_days_in_all_SortFiles(path,aux1));
                else if(set_sections_in_all_SortFiles(aux1));
                else if(set_CoffeBreak_in_all_SortFiles(path,aux1));
                else if(set_lunch_in_all_SortFiles(path,aux1));
                else if(aux1.length()!=0)
                {
                    aux2=getName_semicolon(aux1,0,'(');    //find club's name 
                    
                    i=get_i_nameGroup(aux2);       //Finding the index for the name of the team, and if the name of the team is not, the function returns a value of negative one
//                    System.out.print("!!!"+i+aux2+" , ");
                    
                    if(i!=-1)       //This condition means that the word found is one of the team names 
                    {
                        aux3="("+getName_semicolon(aux1,1,'(') +"("+ getName_semicolon(aux1,2,'(');   //Finding the competition time with rest time
                        appendStrToFile(path+groups[i]+".csv", sections[j]+","+aux3+"\n", true);
//                        a[i][j]=aux1;
                    }
                }
            }
        }
        
        f11.close();
//        return a;
    }
    
    boolean set_lunch_in_all_SortFiles(String path,String str) throws IOException  //Write the name of lunch on all files
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;

        if(str.equals("lunch from 12:00 to 13:00"))
            test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //Write the name of lunch on all files
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_CoffeBreak_in_all_SortFiles(String path,String str) throws IOException  //كتابة اسم الكوفي بريك على جميع الملفات
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;

        if(str.equals("coffe break from 10:00 to 10:15"))
            test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //Write the name of coffe break on all files
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_days_in_all_SortFiles(String path,String str) throws IOException  //Write the name of day on all files
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;
        for(i=0;i<5;i++)
            if(days[i].equals(str))
                test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //Write the name of day on all files
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_sections_in_all_SortFiles(String str) throws IOException   
    {
        boolean test=false;
        if(sections[0].equals(str))
            test=true;
        
        return test;      
    }
    
    int get_i_nameGroup(String str)     //This function to finding the index for the team name, ie, this function works to replace the team name with the index number
    {
        int i;
//        String groups[]=new String[]{"7_8_F","7_8_M","9_10_F","9_10_M","11_12_F","11_12_M"
//        ,"13_14_F","13_14_M","15_16_F","15_16_M","17_18_F","17_18_M","18_large_F","18_large_M"};
        
        for(i=0;i<14;i++)
            if(groups[i].equals(str))
                return i;
        return -1;
    }
    //=================================Sort Group and Games=============================================
    //==============================================================================================================
    //Finds the real-time array of all sorting files
    //It is an array that includes in each line a team in which the duration of the allotted time to play at each station  
    int[][] analysis_all_games() throws IOException         //Finds the real-time array of all sorting files

    {
        int i,j;
        int all[][]=new int[14][9];
        String str_name[]=new String[]{"7_8_F.csv","7_8_M.csv","9_10_F.csv","9_10_M.csv","11_12_F.csv","11_12_M.csv"
        ,"13_14_F.csv","13_14_M.csv","15_16_F.csv","15_16_M.csv","17_18_F.csv","17_18_M.csv","18_large_F.csv","18_large_M.csv"};
        int join1[][];
        
        for(i=0;i<14;i++)
        {
            //To find the subscription array for one of the sort files
            //Join_file[] -->   (Power Point)
            join1=analysis_parts("files_analysis\\"+str_name[i]);  //o find the subscription array for a file 
            //time[]-->3*m1     (Power Point)
            all[i]=analysis_parts_totalTime(join1);         //To find the time matrix that contains the estimated time for each place  or station
        }
        
        
        return all;
    }
    int[][] analysis_parts(int arr[][])     //To find the subscription array of a game for the real-time array of games
    {
        int i,j;
        int join1[][]=new int[arr.length][9];
        
        for(i=0;i<arr.length;i++)
            for(j=0;j<arr[0].length;j++)
                if(arr[i][j]!=0)
                    join1[i][j]=0;
                else
                    join1[i][j]=1;
        
        return join1;
    }
    
    //To find the subscription array for each player contained within a file where in each line there are zeros and ones
    //The zero indicates that the player is involved in the game, and the one indicates that the player is not involved in the game
    int[][] analysis_parts(String fileName) throws IOException  //To find the subscription array for a set game for a set fileا
    {
        int i,j,n;
        String str;
        int join1[][];
        //find total names in the file 
        BufferedReader file1=new BufferedReader(new FileReader(fileName));
        for(n=0;((str = file1.readLine()) != null);n++);  //find total players in the file
        file1.close();
        
        //declare array for the game 
        join1=new int[n][11];
        
        // find the subscription array for games
        file1=new BufferedReader(new FileReader(fileName));
        
        for(i=0;((str = file1.readLine()) != null);i++)
        {
            for(j=0;j<11;j++)                    //sort games 
                if(getName_semicolon(str,j+4,',').length()==0)
                join1[i][j]=1;   //Not participating in the game is  (one,1)
                else
                join1[i][j]=0; 
        }

        file1.close();
        return join1;
    }
    
    int [] analysis_parts_totalTime(int a[][])  //To find the time array that contains the estimated time for each game or station
    {
        int i;
//        double time_calculate;
        int games_count[]=new int[11];
        int total_time[]=new int[9];     //For the final time for every number of places we have
        
        //Count_file[]-->3*m   (Power Point)
        for(i=0;i<11;i++)          //To find the number of participants in each game
        games_count[i]=analysis_parts_n(a,i);
        
        //-----------------Print the number of participants in each game-----------------------
//        System.out.print("games_counts: \n");
//        for(i=0;i<11;i++)
//            System.out.print(games_count[i]+" , ");
//        System.out.println("");
        //-----------------Print the number of participants in each game-----------------------
        
        //total time for Sprint
        total_time[0]=analysis_parts_time(games_count[0],8,1) + analysis_parts_time(games_count[1],8,1);
        
        //total for Running circal without Sprint
        total_time[1]=analysis_parts_time(games_count[2],6,3) + analysis_parts_time(games_count[3],6,5) 
                + analysis_parts_time(games_count[4],6,10) + analysis_parts_time(games_count[5],6,10);
        
        //total time for long/triple to I and II
        total_time[2]=((int)(games_count[6]+games_count[7])/2)*3;
        total_time[3]=analysis_parts_time((games_count[6]+games_count[7]),2,3);
        
        //total time for High_Jump to I and II
        total_time[4]=((int)games_count[8]/2)*3;
        total_time[5]=analysis_parts_time(games_count[8],2,3);
        
        //total time for Pole_Jump
        total_time[6]=analysis_parts_time(games_count[9],1,3);
        
        //total time for Shot_Thtowing to I and II
        total_time[7]=((int)games_count[10]/2)*5;
        total_time[8]=analysis_parts_time(games_count[10],2,5);
        
        //time[]-->1*m1   (Power Point)
        return total_time;
    }
    
    int analysis_parts_n(int a[][],int colom)   //Find the number of participants for a set game 
    {
        int i,n=0;
        for(i=0;i<a.length;i++)
            if(a[i][colom]==0)
                n++;
        return n;
    }
    
    int analysis_parts_time(int n,int k,int time1)   //To calculate and estimate the time needed for each game, you take the number of players and return the time needed
    {
        int k1 = 0;
//        if((int)n/k!=0 && k!=1)
//            k1=((double)n/k==(int)n/k)? n/k : n/k+1;
//        else
//        {
//            if(k==1) k1=n;      //If the game is played one by one
//            else k1=1;          //If the game is playing a team by team
//        }
        
        if(k==1)
        {
            k1=n;
        }
        else if(k!=1)
        {
            if((int)n/k!=0)         // the team should not be less than the number K
                k1=((double)n/k==(int)n/k)? n/k : ((n/k)+1);
            else if(n!=0)        //In the case of the number of people not equal to zero, it means we have one team because the first condition is not met
                k1=1;
            else if(n==0)     // In the event that there are no subscribers, K1 must be equal to zero
                k1=0;
        }
        
        k1=k1*time1;
        return k1;
    }
    
    void csv_analysis(String fileName) throws IOException //To sort the given file by age and gender
    {
        int i,j;
        String st,s1,s2,s3,s4;
        String deg[]=new String[11];
        csv_create_files("files_analysis\\");
        
        BufferedReader f11=new BufferedReader(new FileReader(fileName));
        
        //for(i=0;(i<4)&&((st = f11.readLine()) != null);i++);  //To skip the first four lines of the required file because they are only headers and not data
        
        for(i=0;((st = f11.readLine()) != null);i++)
        {
            if(st.charAt(0)==',')          //If the file is finished, sometimes the first character in it will be a comma 
                break; 

            s1=getName_semicolon(st,1,',');    //Find  first name 
            s2=getName_semicolon(st,2,',');    //find last name 
            s3=getName_semicolon(st,3,',');    //finde gender 
            s4=getName_semicolon(st,4,',');    //find age  
            
            for(j=0;j<11;j++)                    //sort games  
                deg[j]=getName_semicolon(st,j+5,',');

            data_sort(s1,s2,s3,s4,deg);

//            System.out.println(s1);
        }
//        System.out.println("read i = "+i);
        
        f11.close();
    }
    
    void csv_create_files(String folderName) throws IOException    //To create project's files that include files, sort data by gender and age
    {
        File file;
        file = new File(folderName);
        file.mkdirs();
        int i;
        
        for(i=0;i<14;i++)
            appendStrToFile(folderName+groups[i]+".csv","",false);
    }
    
    void appendStrToFile(String fileName,String str,boolean b1) throws IOException  //To write on the file or create files
    {
        FileWriter fd=new FileWriter(fileName, b1);
        fd.write(str);
        fd.close();
    }
    
    public String getName_semicolon(String str,int count,char ch) //Finds the pace  that is after any number of commas
    {
        int i,count0=0,i1,i2;
        String aux;
        
        for(i=0;i<str.length();i++)
        {
            if(count0==count)
                break;
            if(str.charAt(i)==ch)
                count0++;
        }
        i1=i;     //start index name
        
        for(i=i1;i<str.length();i++)
        {
            if(str.charAt(i)==ch)
                break;
        }
        i2=i;     //last index name
        
        aux=str.substring(i1,i2);
        
        return aux;
    }
    
    public void data_sort(String s1,String s2,String s3,String s4,String deg[]) throws IOException  //To sort data by gender and age
    {
        int i;
        String name=s1;
        String surname=s2;
        String sex=s3;
        String age=s4;
        String str_line;
        //
        str_line=name+","+surname+","+sex+","+age+",";
        
        for(i=0;i<11;i++)
            str_line=str_line+deg[i]+",";
        
        str_line=str_line+"\n";
        
        if((Atoi(age)==7 || Atoi(age)==8) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\7_8_F.csv",str_line,true);
        if((Atoi(age)==7 || Atoi(age)==8) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\7_8_M.csv",str_line,true);
        
        if((Atoi(age)==9 || Atoi(age)==10) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\9_10_F.csv",str_line,true);
        if((Atoi(age)==9 || Atoi(age)==10) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\9_10_M.csv",str_line,true);
        
        if((Atoi(age)==11 || Atoi(age)==12) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\11_12_F.csv",str_line,true);
        if((Atoi(age)==11 || Atoi(age)==12) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\11_12_M.csv",str_line,true);
        
        if((Atoi(age)==13 || Atoi(age)==14) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\13_14_F.csv",str_line,true);
        if((Atoi(age)==13 || Atoi(age)==14) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\13_14_M.csv",str_line,true);
        
        if((Atoi(age)==15 || Atoi(age)==16) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\15_16_F.csv",str_line,true);
        if((Atoi(age)==15 || Atoi(age)==16) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\15_16_M.csv",str_line,true);
        
        if((Atoi(age)==17 || Atoi(age)==18) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\17_18_F.csv",str_line,true);
        if((Atoi(age)==17 || Atoi(age)==18) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\17_18_M.csv",str_line,true);
        
        if((Atoi(age) > 18) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\18_large_F.csv",str_line,true);
        if((Atoi(age) > 18) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\18_large_M.csv",str_line,true);

    }
    
    double Atof(String s)
    {
        int i, numMinuses = 0, numDots = 0;

        s = s.trim();
        for (i = 0; i < s.length()
                && (s.charAt(i) == '-' || s.charAt(i) == '.' || Character.isDigit(s.charAt(i))); i++)
            if (s.charAt(i) == '-')
                numMinuses++;
            else if (s.charAt(i) == '.')
                numDots++;

        if (i != 0 && numMinuses < 2 && numDots < 2)
            return Double.parseDouble(s.substring(0, i));

        return 0.0;
    }
    
    int Atoi(String str)  //find the number from array of char
    {

        int res = 0;
        for (int i = 0; i < str.length(); i++)
            res = res * 10 + str.charAt(i) - '0';

        return res;
    }
}
