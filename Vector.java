import java.util.Arrays;

public class Vector
{
    int[] data;
    int type; // 0 is vector, 1 is target, 2 is avoid
    public Vector (int[] vector, int type)
    {
        data = vector;
        this.type = type;
    }
    
    public int hashCode()
    {
        return Arrays.hashCode(data);
    }
    
    public boolean equals(Object b) 
    {
        if (!(b instanceof Vector))
        {
            return false;
        }
        int[] v1 = data;
        int[] v2 = ((Vector)b).data;
        
        if (Arrays.equals(v1,v2))
        {
            return true;
        }
        
        return false;
    }
    
    public int compareTo(Vector other)
    {
        double dist1 = Math.sqrt(Math.pow(data[0],2)+Math.pow(data[1],2));
        double dist2 = Math.sqrt(Math.pow(other.data[0],2)+Math.pow(other.data[1],2));
        
        if (dist1<dist2)
        {
            return 0;
        }
        
        return 1;
    }

    public String print ()
    {
        return data[0]+","+data[1]+","+type;
    }
}
