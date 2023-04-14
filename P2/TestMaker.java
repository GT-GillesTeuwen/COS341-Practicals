public class TestMaker {
    
    public static void main(String[] args) {
        
        CFG cfg=new CFG();
        String c=(cfg.create());

        while(c.length()<1000){
             c=(cfg.create());
        }

        System.out.println(c);
      
    }
}
