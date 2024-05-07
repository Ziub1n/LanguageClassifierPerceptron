import java.util.List;
import java.util.Map;

public class Language {

    String name;
    List<Map<Character,Double>> mapList;

    public Language(String name, List<Map<Character, Double>> mapList) {
        this.name = name;
        this.mapList = mapList;
    }


    public String getName() {
        return name;
    }

    public List<Map<Character, Double>> getMapList() {
        return mapList;
    }
}