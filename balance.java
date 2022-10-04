/*
Creator:     ANANT PRAKASH SINGH
Year:        2017
Description: This Program balances any non redox chemical equation using bruteforce
*/

import java.util.*;
class balance
{
    static int maxcoef=10;
    public static void main(String[] args)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter any Chemical Equation: ");
        String equation=sc.nextLine();
        System.out.println("Bruteforcing all combinations...");
        String eq=equation;
       
        eq=eq.replace("->","=");
        eq=removeSpace(eq);
        eq=eq.replace("+"," ");
        int p=eq.indexOf("=");
        String stlhs=eq.substring(0,p);
        String strhs=eq.substring(p+1);

        String lhs[]=stringToArray(stlhs);
        String rhs[]=stringToArray(strhs);

        String safelhs[]=new String[lhs.length];
        String saferhs[]=new String[rhs.length];
        System.arraycopy(lhs,0,safelhs,0,lhs.length);
        System.arraycopy(rhs,0,saferhs,0,rhs.length);
        
        lhs=arraySimpler(lhs);
        rhs=arraySimpler(rhs);

        // MAIN PROGRAM

        int l1=lhs.length;
        int l2=rhs.length;
        int i1,i2;
        int maxi1=(int)Math.pow(10,l1*2);
        int maxi2=(int)Math.pow(10,l2*2);

        String[] coef1=new String[l1];
        String[] coef2=new String[l2];

        String[] LHS=new String[l1];
        String[] RHS=new String[l2];

        int i;
        boolean balanced=false;
        String str="";
        
        for(i1=(int)Math.pow(10,l1*2-2);i1<maxi1;i1++)
        {
            
            coef1=intToStringArray(i1);
            if(containZero(coef1))
            {
                continue;
            }
            
            for(i2=(int)Math.pow(10,l2*2-2);i2<maxi2;i2++)
            {
                coef2=intToStringArray(i2);
                if(containZero(coef2))
                {
                    continue;
                }
                LHS=arrayAdder(coef1,lhs);
                RHS=arrayAdder(coef2,rhs);

                System.out.println(java.util.Arrays.toString(LHS)+"\t"+java.util.Arrays.toString(RHS));

                LHS=arraySimpler(LHS);
                RHS=arraySimpler(RHS);

                if(isBalanced(LHS,RHS))
                {
                    safelhs=arrayAdder(coef1,safelhs);
                    saferhs=arrayAdder(coef2,saferhs);
                    
                    System.out.print("\n\nQues:\t"+equation+"\nAns:\t");
                    for(i=0;i<safelhs.length-1;i++)
                    {
                        str=trimIt(safelhs[i]);
                        System.out.print(str+" +");
                    }
                    str=trimIt(safelhs[i]);
                    System.out.print(" "+str+" ->");
                    for(i=0;i<saferhs.length-1;i++)
                    {
                        str=trimIt(saferhs[i]);
                        System.out.print(str+" +");
                    }
                    str=trimIt(saferhs[i]);
                    System.out.print(" "+str);
                    balanced=true;
                }
                if(balanced)
                {
                    break;
                }
            } 
            if(balanced)
            {
                break;
            }
        }
        if(balanced)
        {
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("\t\t\tBy-");
            System.out.println("\t\t\t\tAnant Prakash Singh");
            System.out.println("\t\t\t\t      X-B");
            System.out.println("\t\t\t\t   2017-2018");
        }
        else
        {
            System.out.println("Sorry, the equation you entered can't be balanced.");
            System.out.println("Please check the equation and try again");    
        }
    }

    static String simpler(String c) //TO CONVERT "2H2SO4" -> "HHHHSSOOOOOOOO"
    {
        /*to remove (OH)2 molecule
        int l=c.length();
        for(int i=0;i<l;i++)
        {
            
        }
        */
       
       //to store coefficient of compound. Ex- 2H2SO4 ->  2
        String coefficient="";
        int p=-1,coef;
        int l=c.length();
        int i=0;
        while(Character.isDigit(c.charAt(i)))
        {
            coefficient=coefficient+c.charAt(i);
            p=i;
            i++;
        }
        c=c.substring(p+1);
        if(coefficient=="")
        {
            coef=1;
        }
        else
        {
            coef=Integer.parseInt(coefficient);
        }

        // to insert 1 in between elements. Ex- Na2SO4 ->  Na2S1O4
        c=c+" ";
        l=c.length();
        char ch1,ch2;
        String s="";
        for (i=0;i<l-1;i++)
        {
            ch1=c.charAt(i);
            ch2=c.charAt(i+1);
            s=s+ch1;
            if(Character.isUpperCase(ch1) && Character.isUpperCase(ch2))
            {
                s=s+"1";
            }
            else if(Character.isLowerCase(ch1) && Character.isUpperCase(ch2))
            {
                s=s+"1";
            }
        }
        if(Character.isDigit(s.charAt(s.length()-1))==false)
        {
            s=s+"1";
        }

        //to insert space in between two elements. Ex- Na21SO4 -> Na2 S1 O4
        c="";
        s=s+" ";
        l=s.length();
        for (i=0;i<l-1;i++)
        {
            ch1=s.charAt(i);
            ch2=s.charAt(i+1);
            c=c+ch1;
            if(Character.isDigit(ch1) && Character.isUpperCase(ch2))
            {
                c=c+" ";
            }
        }

        //to do the main work by Scanner class
        Scanner sc=new Scanner(c);
        String str="";
        int dig=0;
        String digit="";
        String ele="";
        String cmpd="";
        while(sc.hasNext())
        {
            str=sc.next();
            l=str.length();
            if(Character.isDigit(str.charAt(l-2)))
            {
                ele=str.substring(0,l-2);
                digit=str.substring(l-2);
            }
            else
            {
                ele=str.substring(0,l-1);
                digit=str.substring(l-1);
            }
            dig=Integer.parseInt(digit);
            for(i=1;i<=dig*coef;i++)
            {
                cmpd=cmpd+ele;
            }
        }
        return cmpd;
    }

    static boolean isBalanced(String ar1[],String ar2[])
    {
        String s1=arrayToString(ar1);
        String s2=arrayToString(ar2);

        s1=insertSpace(s1);
        s2=insertSpace(s2);

        String[] a1=stringToArray(s1);
        String[] a2=stringToArray(s2);

        a1=alphabetical(a1);
        a2=alphabetical(a2);

        s1=arrayToString(a1);
        s2=arrayToString(a2);

        boolean k=s1.equals(s2);
        return k;
    }

    static String insertSpace(String s)
    {
        String c="";
        s=s+" ";
        int l=s.length();
        for (int i=0;i<l-1;i++)
        {
            char ch1=s.charAt(i);
            char ch2=s.charAt(i+1);
            c=c+ch1;
            if(Character.isLowerCase(ch1) && Character.isUpperCase(ch2))
            {
                c=c+" ";
            }
            if(Character.isUpperCase(ch1) && Character.isUpperCase(ch2))
            {
                c=c+" ";
            }
        }
        return c;
    }

    static String[] stringToArray(String s)
    {
        Scanner sc=new Scanner(s);
        String str;
        String[] ar=new String[noOfSpace(s)+1];
        int i=0;
        while(sc.hasNext())
        {
            str=sc.next();
            ar[i]=str;
            i++;
        }
        return ar;
    }

    static String[] alphabetical(String[] s)
    {
        String str="";
        int l=s.length;
        for(int i=0;i<l-1;i++)
        {
            for(int j=i+1;j<l;j++)
            {
                if(s[i].compareTo(s[j])>0)
                {
                    str=s[i];
                    s[i]=s[j];
                    s[j]=str;
                }
            }
        }
        return s;
    }

    static String arrayToString(String[] s)
    {
        String str="";
        for(int i=0;i<s.length;i++)
        {
            str=str+s[i];
        }
        return str;
    }
    
    static String removeSpace(String s)
    {
        String str=s.replace(" ","");
        return str;
    }

    static int noOfSpace(String s)
    {
        int c=0;
        for(int i=0;i<s.length();i++)
        {
            if (s.charAt(i)==32)
            {
                c++;
            }
        }
        return c;
    }

    static boolean containZero(String[] s)
    {
        for(int i=0;i<s.length;i++)
        {
            if(Integer.parseInt(s[i])==0)
            {
                return true;
            }
            else if(Integer.parseInt(s[i])>maxcoef)
            {
                return true;
            }
        }
        return false;
    }

    static String[] intToStringArray(int n)
    {
        String s=Integer.toString(n);
        if (s.length()%2==1)
        {
            s="0"+s;
        }
        int l=s.length();
        String[] ar=new String[l/2];
        String str;
        s=s+" ";
        for(int i=0;i<l;i+=2)
        {
            str=s.substring(i,i+2);
            ar[i/2]=str;
        }
        return ar;
    }

    static String[] arraySimpler(String[] s)
    {
        for(int i=0;i<s.length;i++)
        {
            s[i]=simpler(s[i]);
        }
        return s;
    }

    static String[] arrayAdder(String a1[],String a2[])
    {
        String[] a=new String[a1.length];
        for(int i=0;i<a2.length;i++)
        {
            a[i]=a1[i]+a2[i];
        }
        return a;
    }
    
    static String trimIt(String s)
    {
        s=s.trim();
        if(s.substring(0,2)=="00" || s.substring(0,2)=="01")
        {
            s=s.substring(2);
        }
        if(s.charAt(0)=='0')
        {
            s=s.substring(1);
        }
        if(s.charAt(0)=='1' && Character.isUpperCase(s.charAt(1)))
        {
            s=s.substring(1);
        }
        return s;
    }
}