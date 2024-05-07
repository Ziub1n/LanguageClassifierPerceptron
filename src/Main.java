import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String address = "./LanguageData";

        List<Language> languageList = new ArrayList<>();
        List<String> languages = DataBuilder.getListOfLanguages(address);

        for (String name : languages) {
            List<StringBuilder> stringBuilderList = DataBuilder.getListOfAllFiles(address + File.separator + name);
            if (stringBuilderList.isEmpty()) {
                System.out.println("Can't find language file: " + name);
                continue;
            }
            List<Map<Character, Double>> listOfProportions = DataBuilder.getListOfProportions(stringBuilderList);
            languageList.add(new Language(name, listOfProportions));
        }

        List<Perceptron> perceptronList = new ArrayList<>();

        for (Language language : languageList) {
            perceptronList.add(new Perceptron(26, 0.05, language.getName()));
        }

        List<Node> trainList = new ArrayList<>();

        for (Language language : languageList) {
            for (Map<Character, Double> singleMap : language.getMapList()) {
                List<Double> attributes = new ArrayList<>();
                for (int i = 'a'; i <= 'z'; i++) {
                    Double count = singleMap.getOrDefault((char) i, 0.0);
                    attributes.add(count);
                }
                trainList.add(new Node(attributes, language.getName()));
            }
        }

        int epoch = 100;

        for (int i = 0; i < languageList.size() * epoch; i++) {
            Collections.shuffle(trainList);
            for (Node node : trainList) {
                for (Perceptron perceptron : perceptronList) {
                    perceptron.learn(node, (node.getName().equals(perceptron.getLanguageName()) ? 1 : 0));
                }
            }
        }

        for (Perceptron perceptron : perceptronList) {
            perceptron.normalize();
        }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text for language recognition (exit to end the program):");
        String inputText;
        while (!(inputText = scanner.nextLine()).equalsIgnoreCase("exit")) {
            StringBuilder inputStringBuilder = new StringBuilder(inputText);
            List<StringBuilder> stringBuilderList = new ArrayList<>();
            stringBuilderList.add(inputStringBuilder);
            List<Map<Character, Double>> listOfMapOfProportions = DataBuilder.getListOfProportions(stringBuilderList);
            Node inputNode = getInputNode(listOfMapOfProportions);

            double maxScore = Double.NEGATIVE_INFINITY;
            String foundLanguage = "";
            for (Perceptron perceptron : perceptronList) {
                double score = perceptron.value(inputNode);
                if (score > maxScore) {
                    maxScore = score;
                    foundLanguage = perceptron.getLanguageName();
                }
            }

            System.out.println("Predicted Language: " + foundLanguage);
            System.out.println("Enter next text for language recognition (exit to end the program):");
        }
        scanner.close();
    }

    private static Node getInputNode(List<Map<Character, Double>> listOfMapOfProportions) {
        Map<Character, Double> proportionsMap = listOfMapOfProportions.get(0);

        List<Double> input = new ArrayList<>();
        for (int i = 'a'; i <= 'z'; i++) {
            if (proportionsMap.containsKey((char) i)) {
                input.add(proportionsMap.get((char) i) / 26.0);
            } else {
                input.add(0.0);
            }
        }

        Node inputNode = new Node(input, "unknown");
        inputNode.normalize();
        return inputNode;
    }
}