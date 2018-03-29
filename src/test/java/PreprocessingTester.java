import Preprocessing.Preprocessing;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PreprocessingTester {

    Preprocessing preprocessing;
    String initialMessage1;

    List<String> result;

    @Before
    public void setUp(){
        preprocessing = new Preprocessing();
        /*FROM: https://www.nytimes.com/2018/03/29/world/europe/russia-expels-diplomats.html */
        initialMessage1 = "Intensifying Russia’s clash with Europe and the United States, the Kremlin on Thursday announced that it would expel 150 Western diplomats and close the American consulate in St. Petersburg.\n" +
                "\n" +
                "The action was in retaliation for the expulsion of more than 150 Russian officials from other countries — which was itself a reaction to a nerve-agent attack on British soil that Britain and its allies have blamed on Moscow.\n" +
                "\n" +
                "The United States ambassador to Russia, Jon M. Huntsman Jr., was summoned to the Foreign Ministry, the foreign minister, Sergey V. Lavrov, announced. Sixty American diplomats will be expelled from Russia — the same as the number of Russian diplomats whom Washington has expelled.";
    }

    @Test
    public void test1(){
        result = preprocessing.conductPreprocessing(initialMessage1);
        System.out.println(result);
    }

}
