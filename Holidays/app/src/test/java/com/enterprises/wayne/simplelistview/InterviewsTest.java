package com.enterprises.wayne.simplelistview;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class InterviewsTest
{
    @Test
    public void testFailFastIterator()
    {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("a", 1);
        hashMap.put("b", 2);
        hashMap.put("c", 3);

        // wrong way to do it
        //for (String key : hashMap.keySet())
        //    if (hashMap.get(key) < 2)
        //        hashMap.remove(key);

        // a way that works
        Iterator<Map.Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, Integer> entry = iterator.next();
            if (entry.getValue() < 2)
                iterator.remove();
        }

        assertEquals(new HashSet<String>(Arrays.asList("b", "c")), hashMap.keySet());
    }

    @Test
    public void testUniqueCharacter()
    {
        assertTrue(uniqueCharacter("abc"));
        assertTrue(uniqueCharacter("vcak3@+-"));
        assertFalse(uniqueCharacter("abca"));
        assertFalse(uniqueCharacter("++"));

    }

    /**
     * assumes each character is ASCII
     * case Sensitive
     */
    boolean uniqueCharacter(String str)
    {
        // optimization, if it has more than 128 characters then at least one of them is repeated
        if (str.length() > 128)
            return false;

        // check if any character was found before
        boolean found[] = new boolean[128];
        for (int i = 0; i < str.length(); i++)
            if (found[str.charAt(i)])
                return false;
            else
                found[str.charAt(i)] = true;
        return true;
    }

    @Test
    public void testIsPermutation()
    {
        assertTrue(isPermutation("abdca", "aadbc"));
        assertTrue(isPermutation("+#-a", "#-a+"));
        assertFalse(isPermutation("abca", "abc"));
        assertFalse(isPermutation("bclad", "bcldd"));
    }

    /**
     * assumes each character is ASCII
     * case sensitive
     */
    private boolean isPermutation(String str1, String str2)
    {
        // optimization, if they are of different size then they are not permutation of each other
        if (str1.length() != str2.length())
            return false;

        // check each character has the same count
        int count[] = new int[128];
        for (int i = 0; i < str1.length(); i++)
            count[str1.charAt(i)]++;

        for (int i = 0; i < str2.length(); i++)
        {
            count[str2.charAt(i)]--;
            if (count[str2.charAt(i)] < 0)
                return false;
        }

        return true;
    }
}