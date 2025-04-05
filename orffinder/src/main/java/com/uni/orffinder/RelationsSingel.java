package com.uni.orffinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
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
 * JavaFX App to show the ORF statistics of different generated Sequences based on different base weights
 */
public class RelationsSingel extends Application {

    /**
     * an Array with the names for the different weights
     */
    final static String[] names = {"10% A", "20% A", "30% A", "40% A", "50% A", "60% A", "70% A", "80% A", "90% A", "10% C", "20% C", "30% C", "40% C", "50% C", "60% C", "70% C", "80% C", "90% C", "10% G", "20% G", "30% G", "40% G", "50% G", "60% G", "70% G", "80% G", "90% G", "10% T", "20% T", "30% T", "40% T", "50% T", "60% T", "70% T", "80% T", "90% T"};
    
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
        for( int APercentage = 10; APercentage <= 90; APercentage+=10 )
        {
            Sequences multipleSequences = new Sequences();
            double[] weights = {APercentage, (100-APercentage)/3 , (100-APercentage)/3, (100-APercentage)/3 };
            for( int i = 0; i < numberOfSequences; i++ )
            {
                Sequence seq;
                try {
                    seq = new Sequence( numberOfBases, weights, reverse);
                    seq.generateOpenReadingFrames();
                    multipleSequences.addSequence( seq );
                } catch (IOException ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            differentWeightedSequences.add( multipleSequences );
        }
        
        for( int CPercentage = 10; CPercentage <= 90; CPercentage+=10 )
        {
            Sequences multipleSequences = new Sequences();
            double[] weights = {(100-CPercentage)/3, CPercentage , (100-CPercentage)/3, (100-CPercentage)/3 };
            for( int i = 0; i < numberOfSequences; i++ )
            {
                Sequence seq;
                try {
                    seq = new Sequence( numberOfBases, weights, reverse);
                    seq.generateOpenReadingFrames();
                    multipleSequences.addSequence( seq );
                } catch (IOException ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            differentWeightedSequences.add( multipleSequences );
        }
        
        for( int GPercentage = 10; GPercentage <= 90; GPercentage+=10 )
        {
            Sequences multipleSequences = new Sequences();
            double[] weights = {(100-GPercentage)/3, (100-GPercentage)/3 , GPercentage, (100-GPercentage)/3  };
            for( int i = 0; i < numberOfSequences; i++ )
            {
                Sequence seq;
                try {
                    seq = new Sequence( numberOfBases, weights, reverse);
                    seq.generateOpenReadingFrames();
                    multipleSequences.addSequence( seq );
                } catch (IOException ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            differentWeightedSequences.add( multipleSequences );
        }
        
        for( int TPercentage = 10; TPercentage <= 90; TPercentage+=10 )
        {
            Sequences multipleSequences = new Sequences();
            double[] weights = {(100-TPercentage)/3, (100-TPercentage)/3 , (100-TPercentage)/3, TPercentage };
            for( int i = 0; i < numberOfSequences; i++ )
            {
                Sequence seq;
                try {
                    seq = new Sequence( numberOfBases, weights, reverse);
                    seq.generateOpenReadingFrames();
                    multipleSequences.addSequence( seq );
                } catch (IOException ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(RelationsSingel.class.getName()).log(Level.SEVERE, null, ex);
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
            final XYChart.Data<String, Number> data;
            if( differentWeightedSequences.get( i ).getBiggestORF() == null )
            {
                data = new XYChart.Data( names[i], 0 );
            }
            else
            {
                data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getBiggestORF().getBases().length() );
            }
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
            final XYChart.Data<String, Number> data;
            data = new XYChart.Data( names[i], differentWeightedSequences.get( i ).getAverageORFLength() );
            data.nodeProperty().addListener((ObservableValue<? extends Node> ov, Node oldNode, final Node node) -> {
                if (node != null) {
                    displayLabelForData(data); 
                }
            });
            seriesAvgORFLength.getData().add( data );
        }

        
        Scene scene  = new Scene(bc,1800,600);
        //bc.getData().addAll(seriesA, seriesC, seriesT, seriesG, seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        bc.getData().addAll(seriesLengthBiggestORF, seriesAvgORFNumber, seriesAvgORFLength);
        //bc.getData().addAll(seriesAvgORFNumber, seriesAvgORFLength);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * 
     * @param args the arguments given when App is started
     * @param numberSequences the number of sequences to generate per weight
     * @param numberBasesPerSequence the number of bases to generate per sequence
     * @param reverseBases a boolean if the ORFs on the reverse String should be looked at
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