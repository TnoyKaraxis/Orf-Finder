package com.uni.orffinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * JavaFX App to show the ORF statistics of different generated Sequences based on different AT weights
 */
public class RelationsPairs extends Application {

    /**
     * an Array with the names for the different weights
     */
    final static String[] names = {"10% AT", "20% AT", "30% AT", "40% AT", "50% AT", "60% AT", "70% AT", "80% AT", "90% AT"};
    
    /**
     * the number of Sequences to generate per weight
     */
    static int numberOfSequences;
    
    /**
     * the number of bases to generate per sequence
     */
    static int numberOfBases;
    
    /**
     * the boolean if the ORFs on the reverse String should be looked at
     */
    static boolean reverse;
 
    /**
     * creates the bar charts
     * @param stage
     */
    @Override public void start(Stage stage) {
        ArrayList<Sequences> differentWeightedSequences = new ArrayList<>();
        for( int ATPercentage = 10; ATPercentage <= 90; ATPercentage+=10 )
        {
            Sequences multipleSequences = new Sequences();
            double[] weights = {ATPercentage/2, (100-ATPercentage)/2 , (100-ATPercentage)/2, ATPercentage/2 };
            for( int i = 0; i < numberOfSequences; i++ )
            {
                Sequence seq;
                try {
                    seq = new Sequence( numberOfBases, weights, reverse);
                    seq.generateOpenReadingFrames();
                    multipleSequences.addSequence( seq );
                } catch (IOException ex) {
                    Logger.getLogger(RelationsPairs.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RelationsPairs.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            differentWeightedSequences.add( multipleSequences );
        }
        
        
        
        
        
        
        
        stage.setTitle("Sequences with different weights");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<>(xAxis,yAxis);
        bc.setTitle("Average number of ORFs and Lengths in " + numberOfSequences + " Sequences with " + numberOfBases + " Bases");
        xAxis.setLabel("ORFs and Bases");       
        yAxis.setLabel("Value");
 
        XYChart.Series seriesA = new XYChart.Series();
        seriesA.setName("A");
        for( int i = 0; i < names.length; i++ )
        {
             final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAvgDifferentBasesTotal().get( 'A' ) );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
             });
            seriesA.getData().add( data );
        }
        
        XYChart.Series seriesC = new XYChart.Series();
        seriesC.setName("C");
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAvgDifferentBasesTotal().get( 'C' ) );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesC.getData().add( data );
        }
        XYChart.Series seriesT = new XYChart.Series();
        seriesT.setName("T");
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAvgDifferentBasesTotal().get( 'T' ) );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesT.getData().add( data );
        }
        XYChart.Series seriesG = new XYChart.Series();
        seriesG.setName("G");
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAvgDifferentBasesTotal().get( 'G' ) );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesG.getData().add( data );
        }
        
        XYChart.Series seriesLengthBiggestORF = new XYChart.Series();
        seriesLengthBiggestORF.setName( "Biggest ORF length" );
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getBiggestORF().getBases().length() );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesLengthBiggestORF.getData().add( data );
        }
        
        XYChart.Series seriesAvgORFNumber = new XYChart.Series();
        seriesAvgORFNumber.setName( "Average ORF number per sequence" );
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data =new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAverageORFNumber() );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesAvgORFNumber.getData().add( data );
        }
        
        XYChart.Series seriesAvgORFLength = new XYChart.Series();
        seriesAvgORFLength.setName( "Average ORF Length" );
        for( int i = 0; i < names.length; i++ )
        {
            final XYChart.Data<String, Number> data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAverageORFLength() );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesAvgORFLength.getData().add( data );
        }
        
        /**
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(austria, 57401.85));
        series2.getData().add(new XYChart.Data(brazil, 41941.19));
        series2.getData().add(new XYChart.Data(france, 45263.37));
        series2.getData().add(new XYChart.Data(italy, 117320.16));
        series2.getData().add(new XYChart.Data(usa, 14845.27));  
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(austria, 45000.65));
        series3.getData().add(new XYChart.Data(brazil, 44835.76));
        series3.getData().add(new XYChart.Data(france, 18722.18));
        series3.getData().add(new XYChart.Data(italy, 17557.31));
        series3.getData().add(new XYChart.Data(usa, 92633.68));  
        */
        
        
        
        
        Scene scene  = new Scene(bc,800,600);
        //bc.getData().addAll(seriesA, seriesC, seriesT, seriesG, seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        bc.getData().addAll(seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        //bc.getData().addAll(seriesAvgORFNumber, seriesAvgORFLength);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * the main method that launches the Bar chart creation
     * @param args the arguments given when App is started
     * @param numberSequences the number of Sequences to generate per AT-weight
     * @param numberBasesPerSequence the number of Bases to generate per Sequence
     * @param reverseBases a boolean if the reverse Bases of the Sequences should be looked at
     */
    public static void main(String[] args, int numberSequences, int numberBasesPerSequence, boolean reverseBases ) {
        numberOfSequences = numberSequences;
        numberOfBases = numberBasesPerSequence;
        reverse = reverseBases;
        launch(args);
    }
    
    /**
     * displays the Labels of the bars
     * @param data the data of the Bars
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