package datastructuretester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import search.Searching;
import static sort.ComplexSort.mergeSort;
import static sort.ComplexSort.quickSort;
import static sort.SimpleSort.bubbleSort;
import static sort.SimpleSort.insertionSort;
import static sort.SimpleSort.selectionSort;

/**
 * A JavaFX 8 program to help experiment with data structures and algorithms.
 *
 * @author Gabe - with help
 */
public class DataStructureTester extends Application {
    Stage pStage;
    TextArea taStatus;
    ScrollPane spStatus;
    TextArea taData;
    ScrollPane spData;

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;
        
        taData = new TextArea();
        spData = new ScrollPane(taData);
        spData.setFitToWidth(true);
        spData.setFitToHeight(true);
        
        taStatus = new TextArea();
        
        spStatus = new ScrollPane(taStatus);
        spStatus.setFitToWidth(true);
        spStatus.setPrefViewportHeight(50);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(myMenuBar());
        borderPane.setCenter(spData);
        borderPane.setBottom(spStatus);

        Scene scene = new Scene(borderPane);
        
        primaryStage.setTitle("Data Structures");
        primaryStage.setScene(scene);
        primaryStage.hide();
        primaryStage.show();
    }

    /**
     *
     * Displays a menu for this application.
     *
     *
     *
     * FYI: menu accelerator key codes are listed at:
     *
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/input/KeyCode.html
     *
     *
     *
     * @return
     *
     */
    public MenuBar myMenuBar() {

        MenuBar myBar = new MenuBar();
        
        final Menu fileMenu = new Menu("File");
        final Menu dataMenu = new Menu("Data");
        final Menu sortMenu = new Menu("Sort");
        final Menu searchMenu = new Menu("Search");
        final Menu helpMenu = new Menu("Help");
        
        myBar.getMenus().addAll(fileMenu, dataMenu, sortMenu, searchMenu, helpMenu);

 
//____________________________File Menu Section_________________________________
        MenuItem newCanvas = new MenuItem("New");
        newCanvas.setOnAction((ActionEvent e) -> {
            taData.clear();
        });

        fileMenu.getItems().add(newCanvas);
        
        MenuItem open = new MenuItem("Open");
        open.setOnAction((ActionEvent e) -> {           
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(pStage);
            if (file != null) {
                readFile(file);
            }
        });

        fileMenu.getItems().add(open);
        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent e) -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(pStage);
            if (file != null) {
                writeFile(file);
            }
        });

        fileMenu.getItems().add(save);
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exit);


//____________________________Data Menu Section_________________________________
        MenuItem miGenerateIntegers = new MenuItem("Generate Integers");
        miGenerateIntegers.setOnAction(e -> {

            StringBuilder sb = new StringBuilder();
            String newLine = "\n";
            for (int i = 0; i < 1000; i++) {
                sb.append(i).append(newLine);
            }
            taData.setText(sb.toString());
        });

        dataMenu.getItems().add(miGenerateIntegers);
        MenuItem miRandom = new MenuItem("Randomize Data");

        miRandom.setOnAction(e -> {
            int[] nums = text2IntArray(taData.getText());
            Random gen = new Random();
            for (int i = 0; i < nums.length; i++) {
                int temp = nums[i];
                int j = gen.nextInt(nums.length);
                nums[i] = nums[j];
                nums[j] = temp;
            }
            taData.setText(intArray2Text(nums));
        });
        
        dataMenu.getItems().add(miRandom);


//____________________________Sort Menu Section_________________________________

        //-----------------BUBBLE SORT ASCENDING--------------------------------
        MenuItem miBubbleSortAsc = new MenuItem("Bubble Sort Ascending");

        miBubbleSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            
            MyTimer.startMicroTime();
            bubbleSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miBubbleSortAsc);
        
        //-----------------BUBBLE SORT DESCENDING--------------------------------
        MenuItem miBubbleSortDsc = new MenuItem("Bubble Sort Descending");

        miBubbleSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");

            MyTimer.startMicroTime();
            bubbleSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miBubbleSortDsc);
       
        //---------------SELECTION SORT ASCENDING-------------------------------
        MenuItem miSelectionSortAsc = new MenuItem("Selection Sort Ascending");
        miSelectionSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            selectionSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miSelectionSortAsc);

        //--------------SELECTION SORT DESCENDING-------------------------------
        MenuItem miSelectionSortDsc = new MenuItem("Selection Sort Descending");
        miSelectionSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            selectionSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miSelectionSortDsc);
        
        //--------------INSERTION SORT ASCENDING--------------------------------
        MenuItem miInsertionSortAsc = new MenuItem("Insertion Sort Ascending");
        miInsertionSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            insertionSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miInsertionSortAsc);

        //--------------INSERTION SORT DESCENDING-------------------------------
        MenuItem miInsertionSortDsc = new MenuItem("Insertion Sort Descending");
        miInsertionSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            insertionSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miInsertionSortDsc);
        
        //--------------QUICK SORT ASCENDING------------------------------------
        MenuItem miQuickSortAsc = new MenuItem("Quick Sort Ascending");
        miQuickSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            quickSort(nums, 0, nums.length-1, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miQuickSortAsc);

        //--------------QUICK SORT DESCENDING-----------------------------------
        MenuItem miQuickSortDsc = new MenuItem("Quick Sort Descending");
        miQuickSortDsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            quickSort(nums, 0, nums.length-1, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miQuickSortDsc);
        
        //-----------------MERGE SORT ASCENDING---------------------------------
        MenuItem miMergeSortAsc = new MenuItem("Merge Sort Ascending");
        miMergeSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            mergeSort(nums, "A");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            taData.setText(intArray2Text(nums));
            taStatus.appendText("\nArray to text finished in " + MyTimer.stopMicroTime() + "us");
        });
        sortMenu.getItems().add(miMergeSortAsc);
        
        //-----------------MERGE SORT DESCENDING---------------------------------
        MenuItem miMergeSortDsc = new MenuItem("Merge Sort Descending");
        miMergeSortAsc.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            MyTimer.startMicroTime();
            mergeSort(nums, "D");
            taStatus.appendText("\nSort finished in " + MyTimer.stopMicroTime() + "us");
            taData.setText(intArray2Text(nums));
        });
        sortMenu.getItems().add(miMergeSortDsc);


//____________________________Search Menu Section_______________________________
        MenuItem miSequentialSearch = new MenuItem("Sequential Search");
        miSequentialSearch.setOnAction(e -> {
            MyTimer.startMicroTime();
            int[] nums = text2IntArray(taData.getText());
            taStatus.setText("Converting text to array took " + MyTimer.stopMicroTime() + "us");
            Scanner sc = new Scanner(System.in);
            taStatus.appendText("\nEnter number to search for: ");
            int key = sc.nextInt();
            MyTimer.startMicroTime();
            taStatus.appendText("\nSearch finished in " + MyTimer.stopMicroTime() + "us");
            taStatus.appendText("\n" + key + "is at " + Searching.sequentialSearch(nums, key));
        });
        searchMenu.getItems().add(miSequentialSearch);
        
        MenuItem miBinarySearch = new MenuItem("Binary Search");
        searchMenu.getItems().add(miBinarySearch);

//____________________________Help Menu Section_________________________________
        MenuItem about = new MenuItem("About");
        about.setOnAction((ActionEvent e) -> {
            String message = "DATA STRUCTURES AND ALGORITHMS: featuring different sort methods.\n";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setTitle("About");
            alert.setHeaderText("v1.5 by The Gabe");
            alert.showAndWait();
        });
        helpMenu.getItems().add(about);
        return myBar;
    }

    
//____________________________File helper methods_________________________________
    private void readFile(File myFile) {
        int y = 0;
        try (Scanner sc = new Scanner(myFile)) {
            taData.clear();
            while (sc.hasNextLine()) {
                taData.appendText(sc.nextLine() + "\n");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataStructureTester.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void writeFile(File myFile) {
        try (PrintWriter writer = new PrintWriter(myFile)) {
            for (String line : taData.getText().split("\\n")) {
                writer.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(DataStructureTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int[] text2IntArray(String s) {
        Scanner sc = new Scanner(s);
        int n = s.split("\n").length;
        int[] nums = new int[n];
        for (int i = 0; sc.hasNextInt(); i++) {
            nums[i] = sc.nextInt();
        }
        return nums;
    }

    public static String intArray2Text(int[] a) {
        StringBuilder sb = new StringBuilder();
        String newLine = "\n";
        for (int value : a) {
            sb.append(Integer.toString(value)).append(newLine);
        }
        return sb.toString();
    }

    /**
     *
     * @param args the command line arguments
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
}
