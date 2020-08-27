import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashMap;

public class Solution7
{
    static int x_toTop;
    static int Guard_toTop;
    static int x_toRight;
    static int Guard_toRight;
    static int x_toLeft;
    static int Guard_toLeft;
    static int x_toBottom;
    static int Guard_toBottom;
    static int x_toGuard;
    static int y_toGuard;
    public static int solution(int[] dimensions, int[] your_position, int[] guard_position, int distance) {
        
        // to top
        x_toTop = dimensions[1] - your_position[1];
        Guard_toTop = dimensions[1] - guard_position[1];
        
        // to right
        x_toRight = dimensions[0] - your_position[0];
        Guard_toRight = dimensions[0] - guard_position[0];
        
        // to left
        x_toLeft = your_position[0];
        Guard_toLeft = guard_position[0];
        
        // to bottom
        x_toBottom = your_position[1];
        Guard_toBottom = guard_position[1];
        
        // direct route
        x_toGuard = guard_position[0] - your_position[0];
        y_toGuard = guard_position[1] - your_position[1];
        
        // quad 1, (pos x, pos y)
        
        int xmax = distance + your_position[0];
        int ymax = distance + your_position[1];
        
        int h_reflects = (int)Math.ceil(xmax/dimensions[0]);
        int v_reflects = (int)Math.ceil(ymax/dimensions[1]);
        
        
        
        ArrayList<int[]> targets = target(guard_position, h_reflects, v_reflects);


       
        
        ArrayList<int[]> avoids = avoid(your_position, h_reflects, v_reflects);
        
        
        

        
        
        HashMap<Vector, Vector> images = new HashMap<Vector, Vector>();
        
        
        
        
        // quad 2, (neg x, pos y)
        int[] temp;
        int tlength = targets.size();
        for (int i=0; i<tlength;i++)
        {
            temp = new int[2];
            int[] target = targets.get(i);
            temp[0] = -1 * target[0];
            temp[1] = target[1];
            targets.add(temp);
        }
        
        
        int alength = avoids.size();
        
        for (int i=0; i<alength;i++)
        {
            temp = new int[2];
            int[] avoid = avoids.get(i);
            temp[0] = -1 * avoid[0];
            temp[1] = avoid[1];
            avoids.add(temp);
        }
        
        
        // quad 3, (neg x, neg y)
        
        for (int i=0; i<tlength;i++)
        {
            temp = new int[2];
            int[] target = targets.get(i);
            temp[0] = -1 * target[0];
            temp[1] = -1 * target[1];
            targets.add(temp);
        }
        
        for (int i=0; i<alength;i++)
        {
            temp = new int[2];
            int[] avoid = avoids.get(i);
            temp[0] = -1 * avoid[0];
            temp[1] = -1 * avoid[1];
            avoids.add(temp);
        }
        
        // quad 4, (pos x, neg y)
        
        for (int i=0; i<tlength;i++)
        {
            temp = new int[2];
            int[] target = targets.get(i);
            temp[0] = target[0];
            temp[1] = -1 * target[1];
            targets.add(temp);
        }
        
        for (int i=0; i<alength;i++)
        {
            temp = new int[2];
            int[] avoid = avoids.get(i);
            temp[0] = avoid[0];
            temp[1] = -1 * avoid[1];
            avoids.add(temp);
        }
        
        
        
        // filtering out too far coordinates
        int counter = 0;
        ArrayList<int[]> temptargets = new ArrayList<int[]>();
        ArrayList<int[]> tempavoids = new ArrayList<int[]>();
        for (int i=0;i<targets.size();i++)
        {
            int[] target = targets.get(i);
            if (distanceto(your_position, target) > distance)
            {
                continue;
            }
            else
            {
                temptargets.add(target);
            }
        }
        
        for (int i=0;i<avoids.size();i++)
        {
            int[] avoid = avoids.get(i);
            if (distanceto(your_position, avoid) > distance)
            {
                continue;
            }
            else
            {
                tempavoids.add(avoid);
            }
        }
        
        
        // finding all the angles/vectors, filtering out the doubles
        
        
        
        for (int i=0;i<temptargets.size();i++)
        {
            Vector target = new Vector(temptargets.get(i),1);
            Vector vector = new Vector(vect(your_position,target.data),0);
            if (!images.containsKey(vector))
            {
                images.put(vector,target);
            }
            else if (target.compareTo(images.get(vector))==0)
            {
                images.put(vector,target);
            }
        }
        
        // filtering out the double avoids
        
        HashMap<Vector, Vector> avoid_images = new HashMap<Vector, Vector>();
        
        for (int i=0;i<tempavoids.size();i++)
        {
            Vector avoid = new Vector(tempavoids.get(i),2);
            Vector vector = new Vector(vect(your_position,avoid.data),0);
            if (!avoid_images.containsKey(vector))
            {
                avoid_images.put(vector,avoid);
            }
            else if (avoid.compareTo(avoid_images.get(vector))==0)
            {
                avoid_images.put(vector,avoid);
            }
        }
        
        // printing avoids
        
        for (Vector i : avoid_images.keySet()) 
            {

                    //System.out.println("vector: " + i.print() + " avoid: " + avoid_images.get(i).print());
                
                
            }
        
        // finding all your angles/vectors
        
        
        
        for (Vector vector : avoid_images.keySet())
        {
            Vector avoid = avoid_images.get(vector);
            if (!images.containsKey(vector))
            {
                images.put(vector,avoid);
            }
            else if (avoid.compareTo(images.get(vector))==0)
            {
                images.put(vector,avoid);
            }
        }
        
        
        
        
        int count = 0;
        
        for (Vector a1 : images.values()) 
            {
                if (a1.type==1)
                {
                    count++;
                }
            }
            
            for (Vector i : images.keySet()) 
            {
                if (images.get(i).type==1)
                {
                    //System.out.println("key: " + i.print() + " value: " + images.get(i).print());
                }
                
            }
            
        
        return count;
    }
    
    public static ArrayList<int[]> target (int[] guard_position, int h_reflects, int v_reflects)
    {
        ArrayList<int[]> targets = new ArrayList<int[]>();
        ArrayList<int[]> targets2;
        // vertical reflects
        targets.add(guard_position);
        for (int count=1;count<=v_reflects;count++)
        {
            int[] temp = new int[2];
            int[] last = new int[2];
            if (count%2==1)
            {
                temp[0] = guard_position[0];
                last = targets.get(count-1);
                temp[1] = last[1] + 2*Guard_toTop;
                targets.add(temp);
            }
            else
            {
                temp[0] = guard_position[0];
                last = targets.get(count-1);
                temp[1] = last[1] + 2*Guard_toBottom;
                targets.add(temp);
            }
        }
        
       
              
        // rows of horizontal reflects based on the vertical reflects
        
        for (int v_count = 0; v_count<=v_reflects;v_count++)
        {
            int[] last = new int[2];
            int[] temp = new int[2];
            targets2 = new ArrayList<int[]>();
            targets2.add(targets.get(v_count));
            for (int h_count = 1; h_count<=h_reflects; h_count++)
            {
                if (h_count%2==1)
                {   
                    last = targets2.get(h_count-1);
                    temp[0] = last[0] + 2*Guard_toRight;
                    temp[1] = last[1];
                    targets2.add(temp);
                }
                else
                {
                    last = targets2.get(h_count-1);
                    temp[0] = last[0] + 2*Guard_toLeft;
                    temp[1] = last[1];
                    targets2.add(temp);
                }
                
                // printing targets
                int[] target = new int[2];
                target[0] = targets2.get(h_count)[0];
                target[1] = targets2.get(h_count)[1];
                targets.add(target);
                
                   
                    
                
            }

        }
        
        return targets;
    }
    
    public static ArrayList<int[]> avoid (int[] your_position, int h_reflects, int v_reflects)
    {
        ArrayList<int[]> avoids = new ArrayList<int[]>();
        ArrayList<int[]> avoids2;
        // vertical reflects
        avoids.add(your_position);
        for (int count=1;count<=v_reflects;count++)
        {
            int[] temp = new int[2];
            int[] last = new int[2];
            if (count%2==1)
            {
                temp[0] = your_position[0];
                last = avoids.get(count-1);
                temp[1] = last[1] + 2*x_toTop;
                avoids.add(temp);
            }
            else
            {
                temp[0] = your_position[0];
                last = avoids.get(count-1);
                temp[1] = last[1] + 2*x_toBottom;
                avoids.add(temp);
            }
        }
        
              
        // rows of horizontal reflects based on the vertical reflects
        
        for (int v_count = 0; v_count<=v_reflects;v_count++)
        {
            int[] last = new int[2];
            int[] temp = new int[2];
            avoids2 = new ArrayList<int[]>();
            avoids2.add(avoids.get(v_count));
            for (int h_count = 1; h_count<=h_reflects; h_count++)
            {
                if (h_count%2==1)
                {   
                    last = avoids2.get(h_count-1);
                    temp[0] = last[0] + 2*x_toRight;
                    temp[1] = last[1];
                    avoids2.add(temp);
                }
                else
                {
                    last = avoids2.get(h_count-1);
                    temp[0] = last[0] + 2*x_toLeft;
                    temp[1] = last[1];
                    avoids2.add(temp);
                }
                
                int[] avoid = new int[2];
                avoid[0] = avoids2.get(h_count)[0];
                avoid[1] = avoids2.get(h_count)[1];
                avoids.add(avoid);
            }
            

        }
           
        return avoids;
    }
    
    public static double distanceto (int[] your_position, int[] coordinate)
    {
        double x = coordinate[0] - your_position[0];
        double y = coordinate[1] - your_position[1];
        double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
        return d;
    }
    
    public static int[] vect (int[] your_position, int[] coordinate)
    {
        int[] v = new int[2];
        v[0] = coordinate[0] - your_position[0];
        v[1] = coordinate[1] - your_position[1];
        int gcd = 1;
        gcd = gcd(Math.abs(v[1]),Math.abs(v[0]));
        
        v[1] = v[1]/gcd;
        v[0] = v[0]/gcd;
        
        return v;
    }
    
    public static int gcd (int a, int b)
    {
        if (a<b)
        {
            int high = b;
            b = a;
            a = high;
        }
        if (b==0)
        {
            if (a==b)
            {
                return 1;
            }
            return a;
        }
        
        return gcd (b, a%b);    
    }
    
    
}

