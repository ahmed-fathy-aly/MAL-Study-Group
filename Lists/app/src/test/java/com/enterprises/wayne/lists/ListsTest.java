package com.enterprises.wayne.lists;

import android.content.pm.PackageManager;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * It's not a test case, It's just a way to run our code without the UI
 */
public class ListsTest
{
    @Test public void testArrayListRandomAccess()
    {
        // initialize with random numbers
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int listSize= 10000;
        for (int i = 0; i < listSize; i++)
            list.add(random.nextInt(100000));

        // random access the list
        int nAccesses = 1000000;
        int count = 0;
        for (int i = 0; i < nAccesses; i++)
        {
            int position = random.nextInt(listSize);
            count += list.get(position);
        }
    }

    @Test public void testLinkedListRandomAccess()
    {
        // initialize with random numbers
        LinkedList<Integer> list = new LinkedList<>();
        Random random = new Random();
        int listSize= 10000;
        for (int i = 0; i < listSize; i++)
            list.add(random.nextInt(100000));

        // random access the list
        int nAccesses = 1000000;
        int count = 0;
        for (int i = 0; i < nAccesses; i++)
        {
            int position = random.nextInt(listSize);
            count += list.get(position);
        }
    }

    @Test
    public void testArrayListAddLast()
    {
        // initialize with random numbers
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int nNumbers = 1000000;
        for (int i = 0; i < nNumbers; i++)
            list.add(random.nextInt(100000));
    }

    @Test
    public void testLinkedListAddLast()
    {
        // initialize with random numbers
        LinkedList<Integer> list = new LinkedList<>();
        Random random = new Random();
        int nNumbers = 1000000;
        for (int i = 0; i < nNumbers; i++)
            list.addLast(random.nextInt(100000));
    }


    @Test
    public void testArrayListInsert()
    {
        // insert random numbers in random positions
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int nNumbers = 10000;
        for (int i = 0; i < nNumbers; i++)
        {
            int position = random.nextInt(list.size() + 1);
            list.add(position, random.nextInt(100000));
        }
    }

    @Test
    public void testLinkedListInsert()
    {
        // insert random numbers in random positions
        LinkedList<Integer> list = new LinkedList<>();
        Random random = new Random();
        int nNumbers = 10000;
        for (int i = 0; i < nNumbers; i++)
        {
            int position = random.nextInt(list.size() + 1);
            list.add(position, random.nextInt(100000));
        }
    }

    @Test
    public void testArrayListRemove()
    {
        // add random numbers
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        int nNumbers = 100000;
        for (int i = 0; i < nNumbers; i++)
            list.add(random.nextInt(100000));

        // remove them all
        while(list.size() > 0)
            list.remove(0);
    }

    @Test
    public void testLinkedListRemove()
    {
        // add random numbers
        LinkedList<Integer> list = new LinkedList<>();
        Random random = new Random();
        int nNumbers = 100000;
        for (int i = 0; i < nNumbers; i++)
            list.add(random.nextInt(100000));

        // remove them all
        while(list.size() > 0)
            list.removeFirst();
    }



}