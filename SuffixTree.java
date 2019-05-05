import java.util.*;
import java.io.*;


public class SuffixTree{
    private List<tNode> nodes = new ArrayList<>();
    
    List<aNode> anodes1 = new ArrayList<>();
    List<aNode> anodes2 = new ArrayList<>();
    List<aNode> anodes3 = new ArrayList<>();
    //aNode single;
    String st;
    int chec;
    static String fileo;
    String w;
    
    
    static class tNode{
        int s, e;
        boolean ise;
        List<Integer> ch = new ArrayList<>();
    }

    class aNode{
        int s, e;
    }

    public SuffixTree(String path){
        String st2;
        try (PrintWriter p = new PrintWriter(new FileOutputStream(fileo, true))) {
        try{
            FileReader in = new FileReader(path);
            BufferedReader br = new BufferedReader(in);
            try{
                st = br.readLine() + "$";
                //st = "banana$";
                nodes.add(new tNode());
                for (int i = 0; i < st.length(); ++i) {
                    addSuffix(i);
                    //System.out.println("Adding:" + st.substring(i));
                }
                
                st2 = br.readLine();
                chec = 0;
                String s1="";
                String s2="";
                int n = Integer.parseInt(st2);
                //System.out.println("THE N is "+ n);
                for(int i=0; i<n; i++){
                    chec = 0;
                    st2 = br.readLine();
                    //System.out.println("Here at "+st2);
                    int j=0;
                    while(j<st2.length()){
                    
                        if(st2.charAt(j)=='*'){
                            //System.out.println("The * ran");
                            chec=100;
                            if(j==0){
                                if(st2.equals("*")){
                                    chec=-1;}
                                else{s1=""; s2=st2.substring(1);chec=1;}
                                }
                            else if(j==st2.length()-1){s1=st2.substring(0,st2.length()-1);s2="";chec=2;}
                            else{s1=st2.substring(0,j);s2=st2.substring(j+1);chec=3;}
                            break;
                        }
                        j++;
                    }
                    
                    //FileOutputStream os = new FileOutputStream((fileo));
                    if(chec==-1){
                        int len = st.length()-1;
                        //System.out.println("The asterisk line st.length is "+len);
                        for(int q=0; q<len;q++){
                            for(int r=q;r<len;r++){
                                //System.out.println(q + " " + r);
                                w = q+ " " + r;
                                p.println(w);
                            }
                        }
                    }
                    else if(chec==0){
                        //System.out.println("chec=0 at "+st2);
                        //System.exit(0);
                        search(st2);
                        int k =0; 
                        while(k<anodes3.size()){
                            if(anodes3.get(k).s<st.length()-1 && anodes3.get(k).e<st.length()-1)
                            {w = anodes3.get(k).s + " " + anodes3.get(k).e;
                            p.println(w);
                        }
                            k++;
                        }
                        anodes3 = new ArrayList<>();
                    }
                    else if(chec==1){
                        //System.out.println("Ran search(S2) and string is "+ s2);
                        //System.out.println("chec=1 at "+st2);
                        search(s2);
                        int k = 0, l = 0;
                        
                        while(k <= anodes2.get(anodes2.size()-1).s){
                            l=0;
                            while(l< anodes2.size() && (anodes2.get(l).s<st.length())){
                                //System.out.println(k + " " + anodes2.get(l).e);
                                if(k<=anodes2.get(l).s){
                                w=k+" "+anodes2.get(l).e;
                                p.println(w);
                              }
                            
                                
                                l++;
                            }
                            k++;
                        }
                        anodes2 = new ArrayList<>();
                    }
                    else if(chec==2){
                        //System.out.println("The string searched is "+ s1);
                        //System.out.println("chec=2 at "+st2);
                        search(s1);
                        int k = 0, l = 0;
                        //System.out.println("l=0 "+anodes1.get(0).s);
                        //System.out.println("l=1 "+anodes1.get(1).s);
                        //if(anodes2.size()>1){anodes2.remove(anodes2.size()-1);}
                        while(l<anodes1.size()){
                            k=0;
                            while((k+anodes1.get(l).e)<st.length()-1){
                                //System.out.println(anodes1.get(l).s + " " + (k+anodes1.get(l).e));
                                w = anodes1.get(l).s + " " + (k+anodes1.get(l).e);
                                //os.write(anodes1.get(l).s + " " + (k+anodes1.get(l).e));
                                //if(k+anodes1.get(l).e != st.length()-2){
                                p.println(w);
                                k++;
                            //}
                                //k++;

                            }
                            l++;
                        }
                        anodes1 = new ArrayList<>();
                    }
                    else if(chec==3){
                        //System.out.println("chec=3 at "+st2);
                        chec=2;
                        //System.out.println("S1 is "+s1);
                        
                        search(s1);
                        
                        chec=1;
                        //System.out.println("S2 is "+s2);
                        
                        search(s2);
                        //System.out.println("anodes2 0 = "+anodes1.get(0).s + " "+anodes1.get(0).e);
                        //System.out.println("anodes2 1 = "+anodes1.get(1).s + " "+anodes1.get(1).e);
                        //System.out.println("anodes2 1 = "+anodes1.get(2).s + " "+anodes1.get(2).e);
                        int k=0,l=0;
                        while(l<anodes1.size()){
                            //System.out.println("In l loop: "+ l);
                            k=0;
                            while(k<anodes2.size()){
                                //System.out.println("In k loop: "+ k);
                                if(anodes1.get(l).e<anodes2.get(k).s && anodes2.get(k).e<st.length()-1){
                                    //os.write(anodes1.get(l).s + " " + anodes2.get(l).e);
                                    //System.out.println(anodes1.get(l).s + " " + anodes2.get(k).e);
                                    w = anodes1.get(l).s + " " + anodes2.get(k).e;
                                    p.println(w);
                                }
                                k++;
                            }
                            l++;
                        }
                        anodes1 = new ArrayList<>();
                        anodes2 = new ArrayList<>();
                    }
                
                }
            }catch (FileNotFoundException e){System.out.println("H");} 
                br.close();
            }catch(IOException f){}
            
        }catch(FileNotFoundException e){}
    }

    
        

    void addSuffix(int x){
        int n = 0;
        int i = x;
        String suf = st.substring(i);
        while ((i-x) < suf.length()) {
            char b = suf.charAt(i-x);
            List<Integer> children = nodes.get(n).ch;
            int x2 = 0;
            int n2;
            while (true) {
                if (x2 == children.size()) {
                    // no matching child, remainder of suf becomes new node.
                    n2 = nodes.size();
                    tNode temp = new tNode();
                    temp.s = i;
                    temp.e = i + suf.substring(i-x).length();
                    nodes.add(temp);
                    children.add(n2);
                    return;
                }
                n2 = children.get(x2);
                //System.out.println("THis is:" + "s = " + nodes.get(n2).s + " e= "+nodes.get(n2).e );
                if (st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(0) == b) break;
                x2++;
            }
            // find prefix of remaining suffix in common with child
            String sub2 = st.substring(nodes.get(n2).s, nodes.get(n2).e);
            int y = nodes.get(n2).s;
            int j = y;
            while ((j - y) < sub2.length()) {
                if (suf.charAt(i - x + j - y) != sub2.charAt(j - y)) {
                    // split n2
                    int n3 = n2;
                    // new node for the part in common
                    n2 = nodes.size();
                    tNode temp = new tNode();
                    //System.out.println("y = "+y + " j = "+j);
                    temp.s = y;
                    temp.e = j;
                    temp.ch.add(n3);
                    nodes.add(temp);
                    nodes.get(n3).s = j;
                    nodes.get(n3).e = j + sub2.substring(j - y).length();  // old node loses the part in common
                    nodes.get(n).ch.set(x2, n2);
                    break;  // continue down the tree
                }
                j++;
            }
            i += j - y;  // advance past part in common
            n = n2;  // continue down the tree
        }
    }

    void search(String tos){
        //int ip=0;
        int y = 0;   
        int n = 0;
        int n2;
        int i = 0;
        int u;
        int n20=0;
        int j0=0;
        int x20=0;
        int j=0;
        String sub2 = "";
        while(true){
            int start = 0;
            int c = 0;
            int check =0;
            while (i < tos.length()) {
                char b = tos.charAt(i);
                List<Integer> children = nodes.get(n).ch;
                int x2 = 0;
            
                while (true) {
                    //System.out.println("x2 is "+ x2);
                    if (x2 == children.size()) {
                        // no matching child, remainder of suf becomes new node.
                        //System.out.println("Could not find");
                        return;
                    }
                    n2 = children.get(x2);
                    
                    //System.out.println("This is: " + st.substring(nodes.get(n2).s, nodes.get(n2).e));
                    u = 0;
                if(y==0){    
                    
                    //System.out.println("CONFIRMATION");
                    //System.out.println("1-character is "+ tos.charAt(i));
                    if (st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(0) == b || b == '?'){ 
                        //System.out.println("1- got in loop at "+ tos.charAt(i));
                        y++;
                        if(c==0){start = nodes.get(n2).s;c++;n20=n2;x20=x2;j0=j;}
                        break;
                    }
                    x2++;    
                }    
                       
                // find prefix of remaining suffix in common with child
                
            
                else{
                    u = 0;
                    check = 0;
                    //System.out.println("b is "+ b);
                    while(u < st.substring(nodes.get(n2).s, nodes.get(n2).e).length() && check==0){
                        //System.out.println("1-character is "+ st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(u));
                        if(c!=0 && st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(u) != b && b != '?'){
                            //System.out.println("We entered this");
                            x2 = x20;
                            n2 = n20;
                            j = j0;
                            sub2 = st.substring(nodes.get(n2).s, nodes.get(n2).e);
                            c=0;
                            i=0;
                            //check=1;
                            break;
                        }
                        //if(c!=0 && (st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(u) == b|| b=='?')){check=1; break;}
                        if (st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(u) == b || b == '?'){ 
                        //System.out.println("#U IS " + st.substring(nodes.get(n2).s, nodes.get(n2).e).charAt(u));
                        //System.out.println("2- got in loop at "+ tos.charAt(i));
                            check = 1;
                            if(c==0){start = nodes.get(n2).s + u;c++;n20=n2;x20=x2;j0=u;}
                            break;
                        }
                    u++;
                    }
                    if(check==1){break;}
                    check = 2;
                    n=n2;
                    //System.out.println("Above x2");
                    x2++;
                    break;
                }  
                }      
                    if(check==2){break;}
                        sub2 = st.substring(nodes.get(n2).s, nodes.get(n2).e);
                        //System.out.println("U is "+u+ " and sub2 length is "+sub2.length());
                        j = u;
                        //System.out.println("sub2= "+ sub2.length());
                        //System.out.println("length of sub2 = "+sub2.length()+" and j = "+ j );
                        while (j < sub2.length()) {
                            //System.out.println("sub2= "+ sub2.length());
                            //System.out.println("3-character is "+ tos.charAt(i));
                            if(c==0 && (tos.charAt(i)==sub2.charAt(j)|| tos.charAt(i)=='?')){
                                //System.out.println("##tos.charAt(i) = " + tos.charAt(i));
                                //System.out.println("3- got in loop at "+ tos.charAt(i));
                                start = nodes.get(n2).s + j;
                                n20=n2;x20=x2;j0=j;
                                c++;
                            }
                            
                            //System.out.println("4-charAt(j) is "+sub2.charAt(j));
                            if (c!=0 && (tos.charAt(i) != sub2.charAt(j) && tos.charAt(i) != '?')) {
                                //System.out.println("4- got in loop at "+ tos.charAt(i));
                                x2 = x20;
                                n2 = n20;
                                j = j0;
                                sub2 = st.substring(nodes.get(n2).s, nodes.get(n2).e);
                                c=0;
                                i=0;
                                //break;
                            }
                            j++;
                            if(c!=0) i++;

                            if(i>=tos.length()){
                                
                                /*if(chec==0){
                                    //System.out.println(start + " " + (start+tos.length()-1));
                                    anodes3
                                    //p.println(start+ " " +(start+tos.length()-1));
                                    //os.write(start + " " + (start+tos.length()-1));
                                }*/
                            

                                aNode tuba = new aNode();
                                tuba.s = start;
                                tuba.e = start+tos.length()-1; 
                                if(chec==0){anodes3.add(tuba);}
                                else if(chec==1){anodes2.add(tuba);}
                                else if(chec==2){anodes1.add(tuba);}
                                x2 = x20;
                                n2 = n20;
                                j = j0 + 1;
                                //System.out.println("st substring is "+ st.substring(nodes.get(n2).s, nodes.get(n2).e));
                                sub2 = st.substring(nodes.get(n2).s, nodes.get(n2).e);
                                i=0;
                                c=0;
                            }
                        }
                        //System.out.println("We left this");
                        //i += j;  // advance past part in common
                        //break; // continue down the tree
                    //System.out.println("Next branch is: "+st.substring(nodes.get(nodes.get(n2).ch.get(0)).s, nodes.get(nodes.get(n2).ch.get(0)).e));
                    //int x = nodes.get(n2).ch.get(0);
                    //System.out.println("Next to next branch is: "+st.substring(nodes.get(nodes.get(x).ch.get(0)).s, nodes.get(nodes.get(x).ch.get(0)).e));
                    n = n2;
                
                
            }    
        }
    
    }
    
    public static void main(String[] args){
        fileo = args[1];
    try{    PrintWriter writer = new PrintWriter(fileo);
        //writer.print("");
        writer.close();
    }catch (FileNotFoundException eager){System.out.println("H");} 
        
        new SuffixTree(args[0]);
        
    }

}