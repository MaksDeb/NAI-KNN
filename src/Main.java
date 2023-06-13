import java.io.*;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Chosse if you want to work on Iris(1) or WDBC(2) data set");
            Scanner datasetchoice = new Scanner(System.in);
            int choice = datasetchoice.nextInt();
            String train_set = "";
            String test_set = "";
            switch (choice){
                case 1:
                     train_set = "C:\\Users\\maksd\\Desktop\\NAI\\KNN nai\\NAI-KNN\\Data\\iris.data";
                     test_set = "C:\\Users\\maksd\\Desktop\\NAI\\KNN nai\\NAI-KNN\\Data\\iris.test.data";
                     break;
                case 2:
                     train_set = "C:\\Users\\maksd\\Desktop\\NAI\\KNN nai\\NAI-KNN\\Data\\wdbc.data";
                     test_set = "C:\\Users\\maksd\\Desktop\\NAI\\KNN nai\\NAI-KNN\\Data\\wdbc.test.data";
                     break;
                default:
                    throw new Exception("Invalid output!");

            }

            File learndata = new File(train_set);
            Scanner filereader = new Scanner(learndata);

            List<double[]> values = new ArrayList<>();
            List<String> names = new ArrayList<>();

            while (filereader.hasNextLine()) {
                String data = filereader.nextLine();
                String[] tab = data.split(",");
                double[] val = new double[tab.length - 1];
                for (int i = 0; i < val.length; i++) {
                    val[i] = Double.parseDouble(tab[i]);
                }
                values.add(val);
                names.add(tab[tab.length - 1]);
            }
            for (int i = 0; i < values.size(); i++) {
                System.out.println(Arrays.toString(values.get(i)) + " " + names.get(i));
            }

            System.out.println("======================================================================");

            File testingdata = new File(test_set);
            filereader = new Scanner(testingdata);

            List<double[]> testvalues = new ArrayList<>();
            List<String> testnames = new ArrayList<>();

            while (filereader.hasNextLine()) {
                String testdata = filereader.nextLine();
                String[] testtab = testdata.split(",");
                double[] testval = new double[testtab.length - 1];
                for (int i = 0; i < testval.length; i++) {
                    testval[i] = Double.parseDouble(testtab[i]);
                }
                testvalues.add(testval);
                testnames.add(testtab[testtab.length - 1]);
            }
            for (int i = 0; i < testvalues.size(); i++) {
                System.out.println(Arrays.toString(testvalues.get(i)) + " " + testnames.get(i));
            }


            System.out.println("==================================================");


            Scanner input = new Scanner(System.in);
            System.out.print("Choose K: ");
            String k = input.nextLine();
            System.out.println();


            int correct = 0;
            int x = 0;

            for (double[] instance : testvalues) {
                String output = predict(values,names,instance,k);
                System.out.println("Predicted output: " + output);

                if(output.equals(testnames.get(x++))){
                    correct++;
                }
            }
            System.out.println(correct);


            double accuracy = (double) correct / testnames.size();
            System.out.println("Accuracy of the model: " + accuracy);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Type 4 parameters or 30 parameters depending on data set you are working");
            String userinput = scanner.nextLine();



            while (!userinput.equals("break")) {
                String[] tab = userinput.split(",");
                double[] test = new double[tab.length];
                for (int i = 0; i < tab.length; i++) {
                    test[i] = Double.parseDouble(tab[i]);
                }
                if (tab.length != 4 && tab.length != 30) {
                    System.out.println("Bad input!");
                } else {
                    String output = predict(values,names,test,k);
                    System.out.println("Predicted output: " + output);
                    System.out.println("Type break if you want to quit");
                }
                userinput = scanner.nextLine();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static double euclideanDistance(double[] instance1, double[] instance2) {
        double sum = 0.0;
        for (int i = 0; i < instance1.length; i++) {
            sum += Math.pow(instance1[i] - instance2[i], 2);
        }
        return Math.sqrt(sum);
    }
    public static String predict(List<double[]> values, List<String> names, double[] val, String k){
        List<DistanceAndLabel> distances = new ArrayList<>();


        for (int i = 0; i < values.size(); i++) {
            double[] trainInstance = values.get(i);
            String trainLabel = names.get(i);
            double distance = euclideanDistance(trainInstance, val);
            distances.add(new DistanceAndLabel(distance, trainLabel));
        }

        distances.sort(DistanceAndLabel::compareTo);

        int[] labelCounts = new int[names.size()];
        for (int i = 0; i < Integer.parseInt(k); i++) {
            String label = distances.get(i).label;
            int index = names.indexOf(label);
            labelCounts[index]++;
        }

        int maxCount = 0;
        String predictedLabel = "";
        for (int i = 0; i < labelCounts.length; i++) {
            if (labelCounts[i] > maxCount) {
                maxCount = labelCounts[i];
                predictedLabel = names.get(i);
            }
        }
        return predictedLabel;
    }
}