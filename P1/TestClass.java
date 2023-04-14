public class TestClass {

    private static int TESTS;
    private static int PASSED;
    private static String testName;

    public static void main(String[] args) {
        List<Integer> l=new List();
        
        startTest("\u001b[38;5;40mAdding head\u001b[0m");
        l.insert(1);
        l.insert(2);
        l.insert(3);
        l.insert(4);
        endTest();

        l.access(4);
        System.out.println("");
        


    }

    public static void startTest(String name){
        testName=name;
        System.out.println("Starting Test:"+name);
        TESTS=0;
        PASSED=0;
    }

    public static boolean assertEquals(Integer actual,Integer expected){
        TESTS++;
        if(actual==expected){
            PASSED++;
            return true;
        }else{
            System.out.println("Test "+TESTS+" failed expected "+expected+" got "+actual);
            return false;
        }
    } 

    private static void endTest(){
        System.out.println("Ending test "+testName);
        System.out.println("Passed "+PASSED+"/"+TESTS);
    }
}
