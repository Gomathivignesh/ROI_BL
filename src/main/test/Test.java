import com.roi.bl.util.UserRole;

public class Test {

    public static void main(String a[]){
        System.out.println(UserRole.ROOT_USER.getGetValue() == new Integer(2));
    }

}
