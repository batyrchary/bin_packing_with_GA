import javax.crypto.Mac;
import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Bin
{
    public int binID;
    public float height;
    public float width;

    public boolean isItOpened=false;
    public float widthLeft;
    public float heightLeft;
    public float totalCapacityLeft;


    public Bin(int binID, float width, float height)
    {
        this.binID=binID;
        this.width=width;
        this.height=height;
        this.isItOpened=false;
        this.widthLeft=width;
        this.heightLeft=height;
        this.totalCapacityLeft=width*height;
    }

    public Bin(Bin m)
    {
        this.binID = m.binID;
        this.width = m.width;
        this.height = m.height;
        this.isItOpened = m.isItOpened;

        this.heightLeft = m.heightLeft;
        this.widthLeft = m.widthLeft;
        this.totalCapacityLeft=m.totalCapacityLeft;
    }

    public static ArrayList<Bin> CopyBins(ArrayList<Bin> bins)
    {
        ArrayList<Bin> ret=new ArrayList<>();

        for(int i=0;i <bins.size(); i++)
        {
            Bin m=new Bin(bins.get(i));
            ret.add(m);
        }
        return ret;
    }

    public static int ContainsBin(ArrayList<Bin> bins, Bin bin)
    {
        int index=-1;

        for(int i=0; i<bins.size();i++)
        {
            if(bins.get(i).binID==bin.binID)
            {
                index=i;
                break;
            }
        }
        return index;
    }

    public float getheightLeft() { return heightLeft; }

    public float getwidthLeft() { return widthLeft; }

    public float getTotalCapacityLeft(){return totalCapacityLeft; }


    public static Bin UpdateBin(Bin m, Item t)
    {
        Bin mUpdated=new Bin(m);

        mUpdated.heightLeft=mUpdated.heightLeft-t.heightRequest;
        mUpdated.widthLeft=mUpdated.widthLeft-t.widthRequest;
        mUpdated.totalCapacityLeft=mUpdated.totalCapacityLeft-t.widthRequest*t.heightRequest;

        mUpdated.isItOpened=true;

        return mUpdated;
    }

    public static ArrayList<Bin> BinSorter(ArrayList<Bin> bins, String type, String typeOfOrder)
    {
        ArrayList<Bin> ret = new ArrayList<>();

        for(int i=0; i<bins.size(); i++)
        {
            Bin m=new Bin(bins.get(i));
            ret.add(m);
        }

        if(type.equals("All"))
        {
            Collections.sort(ret, new Comparator<Bin>() {
                @Override
                public int compare(Bin p1, Bin p2) {

                    return (int) (p1.totalCapacityLeft - p2.totalCapacityLeft); // Ascending
                }
            });
            ret.sort(Comparator.comparingDouble(Bin::getTotalCapacityLeft)); //smallest to largest
        }
        else if(type.equals("Width"))
        {
            Collections.sort(ret, new Comparator<Bin>() {
                @Override
                public int compare(Bin p1, Bin p2) {

                    return (int) (p1.widthLeft - p2.widthLeft); // Ascending
                }
            });
            ret.sort(Comparator.comparingDouble(Bin::getwidthLeft)); //smallest to largest
        }
        else if(type.equals("Height"))
        {
            Collections.sort(ret, new Comparator<Bin>() {
                @Override
                public int compare(Bin p1, Bin p2) {

                    return (int) (p1.heightLeft - p2.heightLeft); // Ascending
                }
            });
            ret.sort(Comparator.comparingDouble(Bin::getheightLeft)); //smallest to largest
        }

        if(typeOfOrder.equals("decreasing"))
            Collections.reverse(ret);           //decreasing

        return ret;
    }

    public static boolean FitsToBin(Bin m, Item t)
    {
        if(m.heightLeft >= t.heightRequest && m.widthLeft >= t.widthRequest)
        // if(m.totalCapacityLeft>=(t.CPUrequest*t.memoryRequest))
        {
            return true;
        }
        return false;
    }
}




