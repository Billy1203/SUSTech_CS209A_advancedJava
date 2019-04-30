import java.io.*;
import java.util.*;

public class WordCount{

    public static StringBuffer input(String[] args) throws IOException{
        InputStream f = new FileInputStream(args[0]);

        InputStreamReader reader = new InputStreamReader(f, args[1]); //Input type

        BufferedReader br= new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        while (br.ready()) {
            sb.append((char)br.read());
        }
        StringBuffer tmp = new StringBuffer(getChinese(sb.toString()));
        return tmp;
    }

    public static String getChinese(String str){
        int len = str.length();
        char[] arr = str.toCharArray();
        ArrayList<Character> charArr = new ArrayList<>();
        for(int i=0; i<len;i++){
            int tem = (int)arr[i];
            if(Integer.parseInt(intToHex(tem), 16)>=0x4e00 && Integer.parseInt(intToHex(tem), 16)<=0x9fa5)
                charArr.add(arr[i]);
        }

        return charArr.toString().replace("[","").replace("]","").replace(" ","").replace(",","");
    }

    public static String intToHex(int n) {
        StringBuffer s = new StringBuffer();
        String a;
        char[] b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }


    // This part of code is from CSDN blogs. https://blog.csdn.net/weixin_33446857/article/details/85123772
    public static <K, V extends Comparable<? super V>> Map<K, V> sortDescend(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });
        Map<K, V> returnMap = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return returnMap;
    }


    public static void sortCode(List<Character> charlist,String[] encoding){
        Collections.sort(charlist, Comparator.comparing(o -> utf8str2(String.valueOf(o),encoding))); // sorting by hex code
    }

    public static void sortChar(List<Character> charlist){
        Collections.sort(charlist, Comparator.comparing(o -> o.charValue()));
    }



    public static Map<Character, Integer> buildMap(StringBuffer sb){
        Map<Character, Integer> map = new LinkedHashMap<Character, Integer>();
        for(int i = 0; i<sb.length();i++){
            Character cr = sb.charAt(i);
            if(map.containsKey(cr)){
                int val = map.get(cr)+1;
                map.put(cr, val);
            }else{
                map.put(cr,1);
            }
        }
        return map;
    }



    public static byte[] utf8(String str, String[] args)throws IOException{
        byte[] bytes = str.getBytes(args[2]);
        /*for(byte b:bytes){
            // System.out.printf("%2X",b);
        }*/

        return bytes;
    }

    public static String utf8str(String str, String[] args){
        try{
            byte[] bytes = utf8(str,args);
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(intToHex((int)aByte%256));
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "";
    }

    public static String utf8str2(String str, String[] args){
        try{
            byte[] bytes = utf8(str,args);
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(String.format("%02X",aByte%256));
            }
            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  "";
    }



    public static void main(String[] args) throws IOException{
        StringBuffer sb = input(args);

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        List<Byte> bytelist = new ArrayList<>(); // all of hex data


        List<Character> charlist = new ArrayList<>(); // the first chinese word
        map = buildMap(sb); // Every choose need this part
        // System.out.println("1"+charlist);

        switch(args[3]){
            case("char"):{
                for(Character s:map.keySet()){
                    charlist.add(s);
                }
                sortChar(charlist);
            }
            break;
            case("code"):{
                for(Character s:map.keySet()){
                    charlist.add(s);
                }
                sortCode(charlist,args);
            }
            break;
            case("count"):{
                map = sortDescend(map); // Only descend choose need this one
                for(Character s:map.keySet()){
                    charlist.add(s);
                }
            }
            default:break;
        }



        // System.out.println("2"+charlist);

        for(int i=0;i<charlist.size();i++){
            // System.out.print(charlist.get(i)+",");
            utf8(charlist.get(i).toString(), args);
            // System.out.print(","+map.get(charlist.get(i))+"\n");
        }


        try{
            File writeName = new File(args[2]+"_Dict_From_"+args[0]);
            writeName.createNewFile();

            try(FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer)){
                for(int i=0;i<charlist.size();i++){
                    String str1 = (charlist.get(i)+", ");
                    out.write(str1);
                    byte[] bytes = charlist.get(i).toString().getBytes(args[2]);

                    for(byte b:bytes){
                        String str2 = String.format("%02X", b);
                        out.write(str2);
                    }
                    String str3 = (","+map.get(charlist.get(i))+"\n");
                    out.write(str3);
                }
                out.flush();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

