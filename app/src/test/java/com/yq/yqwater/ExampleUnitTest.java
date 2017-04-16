package com.yq.yqwater;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void textAdd() {
        String scjy = "职工90%生活10%";
        String[] array = scjy.split("%");

        int[] fens = new int[array.length];
        int index = 0;
        int fen;
        String strNumber = "1234567890";
        for (int j = 0; j < array.length; j++) {
            String s = array[j];
            char[] ch = s.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                Character c = ch[i];
                if (strNumber.contains(c.toString())) {
                    index = i;
                    break;
                }
            }
            fen = Integer.parseInt(s.substring(index, s.length()));
            fens[j] = fen;
            System.out.println("=========" + fens[j]);
        }
//        System.out.println("========" + (90.0 / 100.0));
    }
}