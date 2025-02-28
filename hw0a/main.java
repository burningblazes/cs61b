public class main {

    public static void starTriangle() {
        for (int i=0; i<5;i++){
            String line="";
            for (int j=0; j<i+1; j++){
                line+="*";
                System.out.println(line);
            }
        }
    }


    public static void printIndexed(String s) {
        String output="";
        for (int i = 0; i < s.length(); i++) {
            output += s.charAt(i);
            output += s.length() - 1 - i;
        }
        System.out.println(output);
    }

    public static String stutter(String s) {
        String output="";
        for (int i = 0; i < s.length(); i++) {
            output+= s.charAt(i);
            output+= s.charAt(i);
        }
        return output;
    }

    public static int quadrant(int x,int y) {
        if (x==0 || y==0) {
            return 0;
        } else if (y>0 && x>0) {
            return 1;
        }else if (y>0 && x<0) {
            return 2;
        } else if (y<0 && x<0) {
            return 3;
        }else {return 4;}
    }

    public static void main(String[] args) {
        starTriangle();
        printIndexed("hello");
        System.out.println(stutter("hello"));
        System.out.println(quadrant(1,2));
    }



}