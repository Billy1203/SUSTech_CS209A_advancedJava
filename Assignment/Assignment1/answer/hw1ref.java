
import java.io.*;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class hw1ref {

    static String[] MODE_STRINGS = {"char","code","count"};

    public static void main(String[] args) throws IOException {

        if(args.length!=4){
            System.err.println("Input Error.");
            System.err.println("Usage: java WordCount filename source_encoding target_encoding sort_key");
            return;
        }

        String filename = args[0];
        String sourceEncoding = args[1];
        String targetEncoding = args[2];

        if(!(Charset.isSupported(sourceEncoding) && Charset.isSupported(targetEncoding))){
            System.err.println("Encoding not supported!");
            return;
        }

        Charset sourceCharset = Charset.forName(sourceEncoding);
        Charset targetCharset = Charset.forName(targetEncoding);
        Charset utf8Charset = Charset.forName("UTF-8");


        int mode = -1;
        for (int i = 0; i < MODE_STRINGS.length; i++) {
            if(MODE_STRINGS[i].equals(args[3])){
                mode = i;
                break;
            }
            if(i==MODE_STRINGS.length-1){
                mode = -1;
                break;
            }
        }

        if(mode==-1){
            System.err.println("Invalid sort key.");
            System.err.println("Valid sort key: char, code, count");
        }


        String resultFilename = targetEncoding + "_Dict_From_" + filename;



        Comparator<Map.Entry<Character, Long>> charComparator = Comparator.comparingInt(e -> e.getKey());
        Comparator<Map.Entry<Character, Long>> codeComparator = Comparator.comparingInt(e -> bytesToInt(targetCharset.encode(e.getKey().toString()).array()));
        Comparator<Map.Entry<Character, Long>> countComparator = Map.Entry.<Character, Long>comparingByValue().reversed();
        Comparator<Map.Entry<Character, Long>> chosenComparator = null;

        switch (mode){
            case 0: chosenComparator = charComparator; break;
            case 1: chosenComparator = codeComparator; break;
            case 2: chosenComparator = countComparator; break;
        }


        try(FileInputStream fis = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(fis, sourceEncoding);
            BufferedReader br = new BufferedReader(isr);){


            try(FileOutputStream fos = new FileOutputStream(resultFilename);
                OutputStreamWriter osw = new OutputStreamWriter(fos,utf8Charset);
                BufferedWriter bw = new BufferedWriter(osw))
            {
                br.lines()
                        .flatMap(str -> str.chars().mapToObj(i -> (char)i))
                        .filter(c -> 0x4e00 <= c.charValue() && c.charValue() <= 0x9fa5 )
                        .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                        .entrySet().stream()
                        .sorted(chosenComparator)
                        .map(entry -> String.format("%s, %s, %d\n",entry.getKey(), bytesToHex(entry.getKey().toString().getBytes(targetCharset)), entry.getValue()))
                        .forEach(str -> {
                            try{
                                bw.write(str);
                            } catch (IOException e){
                                System.err.println("write IOException happened!");
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e){
                System.err.println("write IOException happened!");
                e.printStackTrace();
            }

        } catch (IOException e){
            System.err.println("read IOException happened!");
            e.printStackTrace();
        }

    }

    public static String bytesToHex(final byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02X",bytes[i]&0xFF))
                .collect(Collectors.joining());
    }

    public static int bytesToInt(final byte[] bytes){
        return IntStream.range(0, bytes.length)
                .map(idx -> bytes[idx]&0xFF)
                .reduce(0, (a,b)->a*256+b);

    }

}
