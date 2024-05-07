import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataBuilder {

    public static List<String> getListOfLanguages(String address) throws IOException {
        List<String> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(address))) {
            for (Path path : stream) {
                if (Files.isDirectory(path)) {
                    result.add(path.getFileName().toString());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static List<String> getListOfFiles(String address) throws IOException {
        List<String> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(address))) {
            for (Path path : stream) {
                result.add(path.toString());
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public static StringBuilder getFileContent(String fileAddress) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(fileAddress));
        return new StringBuilder(new String(bytes));
    }

    public static List<StringBuilder> getListOfAllFiles(String directoryAddress) throws IOException {
        List<String> fileAddresses = getListOfFiles(directoryAddress);
        List<StringBuilder> list = new ArrayList<>();
        for (String fileAddress : fileAddresses) {
            list.add(getFileContent(fileAddress));
        }
        return list;
    }

    public static List<Map<Character, Double>> getListOfProportions(List<StringBuilder> stringBuilderList) {
        List<Map<Character, Double>> result = new ArrayList<>();
        for (StringBuilder stringBuilder : stringBuilderList) {
            result.add(proportionsCounter(stringBuilder));
        }
        return result;
    }

    private static Map<Character, Double> proportionsCounter(StringBuilder stringBuilder) {
        Map<Character, Double> map = new HashMap<>();
        long allChars = 0;

        String content = stringBuilder.toString().toLowerCase();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= 'a' && c <= 'z') {
                allChars++;
                if (!map.containsKey(c)) {
                    map.put(c, 1.0);
                } else {
                    map.put(c, map.get(c) + 1.0);
                }
            }
        }

        if (allChars > 0) {
            for (char key : map.keySet()) {
                map.put(key, map.get(key) / allChars);
            }
        }

        return map;
    }
}
