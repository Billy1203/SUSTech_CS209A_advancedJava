import javax.xml.stream.events.Characters;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Function;

public class WordCount2 {
    static final Map<String,Comparator<Node>> sortKeyMap= Map.of("char",new achar(),"code",new code(),"count",new count());

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        String filename=args[0];
        String readCharacter=args[1];
        String targetCharacter=args[2];
        String sortKey=args[3];

        File file=new File(filename);
        Scanner scanner=new Scanner(file,readCharacter);
//        FileOutputStream fo=new FileOutputStream(new File(targetCharacter+"_Dict_From_"+filename));
        PrintWriter pw=new PrintWriter(new File(targetCharacter+"_Dict_From_"+filename), Charset.forName("UTF-8"));
        Map<Character,Integer> map=new TreeMap<>();

        while(scanner.hasNext()){
            String str=scanner.next();
            for(char ch:str.toCharArray()){
                map.merge(ch,1,Integer::sum);
            }
        }
        char low=0x4e00,hi=0x9fa5;
        ArrayList<Node> arrayList=new ArrayList<>();
        for(var ch:map.keySet()){
            StringBuilder sb=new StringBuilder();
            var by =ch.toString().getBytes(targetCharacter);
            for(var b:by){
                sb.append(String.format("%02X",b));
            }
            if(ch>=low&&ch<=hi)
                arrayList.add(new Node(ch,sb.toString(),map.get(ch)));
        }

        arrayList.sort(sortKeyMap.get(sortKey));
        for(var s:arrayList){
            pw.println(s);
        }
        pw.flush();

    }

    static class Node{
        private char achar;
        private String code;
        private int count;

        Node(char achar,String code,int count){
            this.achar=achar;
            this.code=code;
            this.count=count;
        }

        @Override
        public String toString() {
            return String.format("%c, %s, %d",achar,code,count);
        }
    }

    static class code implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return o1.code.compareTo(o2.code);
        }
    }

    static class count implements Comparator<Node>{
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.count,o2.count);
        }
    }

    static class achar implements Comparator<Node>{

        @Override
        public int compare(Node o1, Node o2) {
            return Character.compare(o1.achar,o2.achar);
        }
    }
}