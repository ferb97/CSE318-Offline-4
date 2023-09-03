import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static java.lang.Math.abs;

public class Main{
    public static Map<String, List<String> > carAttributesListMap = new HashMap<>();
    public static int nodeCount = 0;

    public static void initializeCarAttributesList(){

        String buyingKey = "buying";
        List<String> buyingList = new ArrayList<>();
        buyingList.add("vhigh");
        buyingList.add("high");
        buyingList.add("med");
        buyingList.add("low");
        carAttributesListMap.put(buyingKey, buyingList);

        String maintKey = "maint";
        List<String> maintList = new ArrayList<>();
        maintList.add("vhigh");
        maintList.add("high");
        maintList.add("med");
        maintList.add("low");
        carAttributesListMap.put(maintKey, maintList);

        String doorsKey = "doors";
        List<String> doorsList = new ArrayList<>();
        doorsList.add("2");
        doorsList.add("3");
        doorsList.add("4");
        doorsList.add("5more");
        carAttributesListMap.put(doorsKey, doorsList);

        String personsKey = "persons";
        List<String> personsList = new ArrayList<>();
        personsList.add("2");
        personsList.add("4");
        personsList.add("more");
        carAttributesListMap.put(personsKey, personsList);

        String lug_bootKey = "lug_boot";
        List<String> lug_bootList = new ArrayList<>();
        lug_bootList.add("small");
        lug_bootList.add("med");
        lug_bootList.add("big");
        carAttributesListMap.put(lug_bootKey, lug_bootList);

        String safetyKey = "safety";
        List<String> safetyList = new ArrayList<>();
        safetyList.add("low");
        safetyList.add("med");
        safetyList.add("high");
        carAttributesListMap.put(safetyKey, safetyList);

        String classValuesKey = "classValues";
        List<String> classValuesList = new ArrayList<>();
        classValuesList.add("unacc");
        classValuesList.add("acc");
        classValuesList.add("good");
        classValuesList.add("vgood");
        carAttributesListMap.put(classValuesKey, classValuesList);
    }

    public static String getPluralityValue(List<InputDataSet> examples){

        String outputKey = "classValues";
        List<String> classValuesList = carAttributesListMap.get(outputKey);
        String highestValue = " ";
        int maxCount = 0;

        for(String valueString: classValuesList){
            int count = 0;
            for(InputDataSet input: examples){
                if(input.getClassValues().equalsIgnoreCase(valueString)){
                    count++;
                }
            }

            if(count > maxCount){
                maxCount = count;
                highestValue = valueString;
            }
        }

        return highestValue;
    }

    public static String leafNodeValue(List<InputDataSet> examples){
        String outputKey = "classValues";
        List<String> classValuesList = carAttributesListMap.get(outputKey);

        for(String valueString: classValuesList){
            int count = 0;
            for(InputDataSet input: examples){
                if(input.getClassValues().equalsIgnoreCase(valueString)){
                    count++;
                }
            }
            if(count == examples.size()){
                return valueString;
            }
        }

        return null;
    }

    public static String getChildAttribute(InputDataSet input, String attribute){
        String attributeChildValue = " ";

        if(attribute.equalsIgnoreCase("buying")){
            attributeChildValue = input.getBuying();
        }
        else if(attribute.equalsIgnoreCase("maint")){
            attributeChildValue = input.getMaint();
        }
        else if(attribute.equalsIgnoreCase("doors")){
            attributeChildValue = input.getDoors();
        }
        else if(attribute.equalsIgnoreCase("persons")){
            attributeChildValue = input.getPersons();
        }
        else if(attribute.equalsIgnoreCase("lug_boot")){
            attributeChildValue = input.getLug_boot();
        }
        else if(attribute.equalsIgnoreCase("safety")){
            attributeChildValue = input.getSafety();
        }

        return attributeChildValue;
    }

    public static double calculateEntropyForAttribute(List<InputDataSet> examples, String attribute){

        List<String> childAttributesList = carAttributesListMap.get(attribute);
        String outputKey = "classValues";
        List<String> classValuesList = carAttributesListMap.get(outputKey);

        double totalEntropy = 0.0;
        for(String childAttribute: childAttributesList){
            int childAttributeCount = 0;

            Map<String, Integer> countOutputMap = new HashMap<>();
            for(String classValue: classValuesList){
                countOutputMap.put(classValue, 0);
            }

            for(InputDataSet input: examples){
                String attributeChildValue = getChildAttribute(input, attribute);

                if(attributeChildValue.equalsIgnoreCase(childAttribute)){
                    childAttributeCount++;
                    int count1 = countOutputMap.get(input.getClassValues());
                    count1++;
                    countOutputMap.put(input.getClassValues(), count1);
                }
            }

            double entropy = 0.0;
            for(String classValue: classValuesList){
                int count1 = countOutputMap.get(classValue);

                if(count1 > 0) {
                    double p = (count1 * 1.0) / childAttributeCount;
                    entropy += (p * (Math.log(1 / p) / Math.log(2)));
                }
            }

            totalEntropy += ((childAttributeCount * 1.0) / examples.size()) * entropy;
        }

        return totalEntropy;
    }

    public static Node decisionTreeLearningAlgorithm(List<InputDataSet> examples, List<String> unusedAttributesList, List<InputDataSet> parentExamples){
        nodeCount++;
        if(examples.size() == 0){
           String value = getPluralityValue(parentExamples);
           Node node = new Node(value);
           node.setLeaf(true);
           return node;
        }

        else if(leafNodeValue(examples) != null){
            String value = leafNodeValue(examples);
            Node node = new Node(value);
            node.setLeaf(true);
            return node;
        }

        else if(unusedAttributesList.size() == 0){
            String value = getPluralityValue(examples);
            Node node = new Node(value);
            node.setLeaf(true);
            return node;
        }

        else{
            double minEntropy = Double.MAX_VALUE;
            String bestAttribute = " ";
            for(String attribute: unusedAttributesList){
                double entropy = calculateEntropyForAttribute(examples, attribute);

                if(entropy < minEntropy){
                    minEntropy = entropy;
                    bestAttribute = attribute;
                }
            }

            Node node = new Node(bestAttribute);
            List<String> childAttributesList = carAttributesListMap.get(bestAttribute);
            unusedAttributesList.remove(bestAttribute);

            for(String childAttribute: childAttributesList){
                List<InputDataSet> childInputDataSetList = new ArrayList<>();
                List<String> unusedAttributesList1 = new ArrayList<>(unusedAttributesList);

                for(InputDataSet input: examples){
                    String attributeChildValue = getChildAttribute(input, bestAttribute);

                    if(attributeChildValue.equalsIgnoreCase(childAttribute)){
                        childInputDataSetList.add(input);
                    }
                }

                Node node1 = decisionTreeLearningAlgorithm(childInputDataSetList, unusedAttributesList1, examples);
                Edge edge = new Edge(childAttribute, node1);
                node.addEdge(edge);
            }

            return node;
        }
    }

    public static void main(String[] args) {
        initializeCarAttributesList();
        String filePath = "car.data";
        List<InputDataSet> inputDataSetList = new ArrayList<>();
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                count++;
                String[] tokens = line.split(",");
                InputDataSet input = new InputDataSet(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
                inputDataSetList.add(input);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        double trainingSetPercentage = 0.8;
        int trainingSetCount = (int)(trainingSetPercentage * count);
//        System.out.println(trainingSetCount);
        int totalIterations = 20;
        double[] correctPercentage = new double[totalIterations];

        List<String> unusedList = new ArrayList<>();
        unusedList.add("buying");
        unusedList.add("maint");
        unusedList.add("doors");
        unusedList.add("persons");
        unusedList.add("lug_boot");
        unusedList.add("safety");

        for(int j = 0; j < totalIterations; j++) {
            nodeCount = 0;
            Collections.shuffle(inputDataSetList);

            List<InputDataSet> trainingSetList = new ArrayList<>();
            for (int i = 0; i < trainingSetCount; i++) {
                trainingSetList.add(inputDataSetList.get(i));
            }

            List<String> unusedAttributesList = new ArrayList<>(unusedList);

            Node rootNode = decisionTreeLearningAlgorithm(trainingSetList, unusedAttributesList, trainingSetList);
            int correct = 0;
            for (int i = trainingSetCount; i < count; i++) {
                Node currentNode = rootNode;
                InputDataSet inputDataSet = inputDataSetList.get(i);

                while (!currentNode.isLeaf()) {
                    String currentAttribute = currentNode.getLabel();
                    String attributeChildValue = getChildAttribute(inputDataSet, currentAttribute);

                    for (Edge edge : currentNode.getChildEdgeList()) {
                        if (edge.getBranch().equalsIgnoreCase(attributeChildValue)) {
                            currentNode = edge.getAttributeNext();
                            break;
                        }
                    }

                }

                String result = currentNode.getLabel();
                if (result.equalsIgnoreCase(inputDataSet.getClassValues())) {
                    correct++;
                }
            }

//            System.out.println("Total Nodes: " + nodeCount);
//            System.out.println(correct + " " + (count - trainingSetCount));
            correctPercentage[j] = ((correct * 1.0) / (count - trainingSetCount)) * 100;
//            System.out.println(correctPercentage[j] + "%");
        }

        double totalAccuracy = 0.0, totalStandardDeviation = 0.0;
        for(int j = 0; j < totalIterations; j++){
            totalAccuracy += correctPercentage[j];
        }

        double meanAccuracy = totalAccuracy / totalIterations;
        System.out.println("Mean Accuracy: " + meanAccuracy + "%");

        for(int j = 0; j < totalIterations; j++){
            totalStandardDeviation += (meanAccuracy - correctPercentage[j]) * (meanAccuracy - correctPercentage[j]);
        }

        double standardDeviation = Math.sqrt(totalStandardDeviation / totalIterations);
        System.out.println("Standard Deviation: " + standardDeviation);
    }
}