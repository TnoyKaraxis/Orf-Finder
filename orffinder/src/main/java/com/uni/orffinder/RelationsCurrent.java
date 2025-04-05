package com.uni.orffinder;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * JavaFX App to show the ORF statistics of the current Sequences
 */
public class RelationsCurrent extends Application {

    /**
     * the generated Sequences
     */
    private static Sequences sequences;
    
    /**
     * the main method to create the bar chart
     * @param args the arguments given when App is started
     * @param multipleSequences the Sequences the ORF values should be calculated from
     */
    public static void main(String[] args, Sequences multipleSequences ) {
        sequences = multipleSequences;
        launch(args);
    }
    /**
     * creates the bar charts
     * @param stage
     * @throws MalformedURLException 
     */
    @Override public void start(Stage stage) throws MalformedURLException {

        stage.setTitle("Current Sequences");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<>(xAxis,yAxis);
        bc.setTitle("Average number of ORFs and Lengths in current Sequences");
        xAxis.setLabel("ORFs and Bases");       
        yAxis.setLabel("Value");
 
        XYChart.Series seriesA = new XYChart.Series();
        seriesA.setName("A");
        
        final XYChart.Data<String, Number> dataA = new XYChart.Data( "current", sequences.getAvgDifferentBasesTotal().get( 'A' ) );
        dataA.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataA);
            }
        });
        seriesA.getData().add( dataA );
        
        
        XYChart.Series seriesC = new XYChart.Series();
        seriesC.setName("C");
        final XYChart.Data<String, Number> dataC = new XYChart.Data( "current", sequences.getAvgDifferentBasesTotal().get( 'C' ) );
        dataC.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataC); 
            }
        });
        seriesC.getData().add( dataC );
        
        XYChart.Series seriesT = new XYChart.Series();
        seriesT.setName("T");

        final XYChart.Data<String, Number> dataT = new XYChart.Data( "current", sequences.getAvgDifferentBasesTotal().get( 'T' ) );
        dataT.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataT); 
            }
        });
        seriesT.getData().add( dataT );

        XYChart.Series seriesG = new XYChart.Series();
        seriesG.setName("G");

        final XYChart.Data<String, Number> dataG = new XYChart.Data( "current", sequences.getAvgDifferentBasesTotal().get( 'G' ) );
        dataG.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataG); 
            }
        });
        seriesG.getData().add( dataG );
        
        XYChart.Series seriesLengthBiggestORF = new XYChart.Series();
        seriesLengthBiggestORF.setName( "Biggest ORF length" );
        
        final XYChart.Data<String, Number> dataBiggest = new XYChart.Data( "current", sequences.getBiggestORF().getBases().length() );
        dataBiggest.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataBiggest);
            }
        });
        seriesLengthBiggestORF.getData().add( dataBiggest );
        
        XYChart.Series seriesAvgORFNumber = new XYChart.Series();
        seriesAvgORFNumber.setName( "Average ORF number per sequence" );

        final XYChart.Data<String, Number> dataNumber =new XYChart.Data( "current", sequences.getAverageORFNumber() );
        dataNumber.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataNumber); 
            }
        });
        seriesAvgORFNumber.getData().add( dataNumber );
        
        XYChart.Series seriesAvgORFLength = new XYChart.Series();
        seriesAvgORFLength.setName( "Average ORF Length" );
        

        final XYChart.Data<String, Number> dataLength = new XYChart.Data( "current", sequences.getAverageORFLength() );
        dataLength.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
            if (node != null) {
                displayLabelForData(dataLength); 
            }
        });
        seriesAvgORFLength.getData().add( dataLength );

        
        
        
        
        Scene scene  = new Scene(bc,800,600);
        //URL url = new File("./Style_Singel.css").toURI().toURL();
        URL url = getClass().getClassLoader().getResource("./Style_Singel.css");
        System.out.println(url);
        if( url == null )
        {
            System.out.println("Recource not found.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        scene.getStylesheets().add( css );
        bc.getData().addAll(seriesA, seriesC, seriesG, seriesT, seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        //bc.getData().addAll(seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        //bc.getData().addAll(seriesAvgORFNumber, seriesAvgORFLength);
        
        stage.setScene(scene);
        
        stage.show();
        
        
    }
    
    /**
     * creates the Labels with the numbers of the bars in the chart
     * @param data the data of the bar chart
     */
    private void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();
        final Text dataText = new Text(data.getYValue() + "");
        node.parentProperty().addListener((ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });

        node.boundsInParentProperty().addListener((ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) -> {
            dataText.setLayoutX(
                    Math.round(
                            bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
                    )
            );
            dataText.setLayoutY(
                    Math.round(
                            bounds.getMinY() - dataText.prefHeight(-1) * 0.5
                    )
            );
        });
    }

}