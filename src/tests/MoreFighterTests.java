//Author: Connor Steele

package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestFighters3.class, TestFighters4.class }) //test 2x units tests first then 2x integration tests
public class MoreFighterTests {

}
