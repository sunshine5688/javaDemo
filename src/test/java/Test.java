/**
 * 〈随便测试〉
 *
 * @author gaolinfang
 * @create 2019/7/2
 * @since 1.0.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        try {
            Lookup lookup = new Lookup("gaolinfang.com", Type.TXT);
            lookup.run();
            if (lookup.getResult() == Lookup.SUCCESSFUL) {
                System.out.println(lookup.getAnswers()[0].rdataToString());
            }
        } catch (TextParseException e) {
            e.printStackTrace();
        }
    }

}
 
