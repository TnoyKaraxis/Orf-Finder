package com.uni.orffinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;




/**
 * Holds multiple Sequences
 * @author Philipp
 */
public final class Sequences
{
    /**
    * The Sequences of this instance
    */
    private final List<Sequence> sequences;
    
    /**
     * Constructor, creates an empty Sequences Object
     */
    public Sequences()
    {
        sequences = new ArrayList<>();
    }
    
    /**
     * Constructor, creates a Sequences Object with the given bases
     * @param allBasesPath the path to a file with all the bases to create the Object with, seperatet by >Sequence_i
     * @param reverse
     * @throws IOException if the input dosent fit
     */
    public Sequences( String allBasesPath, boolean reverse ) throws IOException
    {
        sequences = new ArrayList<>();
        String bases = "";
        Scanner scanner = new Scanner(allBasesPath);
        boolean inSequence = false;
        while(scanner.hasNext())
        {
            String input = scanner.next();
            input = input.trim();
            if( inSequence )
            {
                if( !input.equals( "" ) && input.charAt(0) == '>' )
                {
                    inSequence = false;
                    addSequence( new Sequence( bases, bases.length(), reverse ) );
                    
                    bases = "";
                }
                if( !input.equals( "" ) && ( input.charAt( 0 ) == 'A' || input.charAt( 0 ) == 'C' || input.charAt( 0 ) == 'T'|| input.charAt( 0 ) == 'G' ) )
                {
                    bases = bases + input;
                }
            }
            else if( !input.equals( "" ) && ( input.charAt( 0 ) == 'A' || input.charAt( 0 ) == 'C' || input.charAt( 0 ) == 'T'|| input.charAt( 0 ) == 'G' ) )
            {
                inSequence = true;
                bases = bases + input;
            }
            
            
        }
        if( !bases.equals( "" ) )
        {
            addSequence( new Sequence( bases, bases.length(), reverse ) );
        }
    }
    
    /**
     * adds a Sequence to the Sequences object
     * @param seq the sequence to add
     */
    public void addSequence( Sequence seq)
    {
        sequences.add(seq);
    }
    
    /**
     * generates the ORFs of the sequences
     * @throws Exception 
     */
    public void generateOpenReadingFrames() throws Exception
    {
        for( int i = 0; i < sequences.size(); i++ )
        {
            sequences.get( i ).generateOpenReadingFrames();
        }
    }
    
    /**
     * calculates the average ORF number per sequence and returns it
     * @return the average ORF number per sequence
     */
    public Integer getAverageORFNumber()
    {
        int avgORFNumber = 0;
        for( int i = 0; i < sequences.size(); i++ )
        {
            avgORFNumber += sequences.get( i ).getORFs().size();
        }
        avgORFNumber = avgORFNumber / sequences.size();
        return avgORFNumber;
    }
    
    /**
     * calculates the average ORF lenght per sequence and returns it
     * @return the average ORF length
     */
    public Integer getAverageORFLength()
    {
        int avgORFLength = 0;
        int orfNumber = 0;
        for( int i = 0; i < sequences.size(); i++ )
        {
            Sequence currentSeq = sequences.get( i );
            for( int j = 0; j < currentSeq.getORFs().size(); j++ )
            {
                orfNumber++;
                avgORFLength += currentSeq.getORFs().get( j ).getBases().length();
            }
        }
        if( orfNumber == 0 )
        {
            return 0;
        }
        avgORFLength = avgORFLength / orfNumber;
        return avgORFLength;
    }
    
    /**
     * calculates the different relative base amount
     * @return a map that links the Base to the relative amount of it
     */
    public Map<Character, Double> getDifferentBasesRelative()
    {
        Map<Character, Double> basesRelative = new HashMap<>();
        basesRelative.put( 'A', 0.0 );
        basesRelative.put( 'C', 0.0 );
        basesRelative.put( 'G', 0.0 );
        basesRelative.put( 'T', 0.0 );
        
        
        for( int i = 0; i < sequences.size(); i++ )
        {
            basesRelative.put( 'A', basesRelative.get('A') + sequences.get( i ).getDifferentBasesRelative().get( 'A' ) );
            basesRelative.put( 'C', basesRelative.get('C') + sequences.get( i ).getDifferentBasesRelative().get( 'C' ) );
            basesRelative.put( 'G', basesRelative.get('G') + sequences.get( i ).getDifferentBasesRelative().get( 'G' ) );
            basesRelative.put( 'T', basesRelative.get('T') + sequences.get( i ).getDifferentBasesRelative().get( 'T' ) );
            
            
        }
        
        basesRelative.put( 'A' , basesRelative.get( 'A' ) / sequences.size() );
        basesRelative.put( 'C' , basesRelative.get( 'C' ) / sequences.size() );
        basesRelative.put( 'G' , basesRelative.get( 'G' ) / sequences.size() );
        basesRelative.put( 'T' , basesRelative.get( 'T' ) / sequences.size() );
        
        
        return basesRelative;
    }
    
    /**
     * calculates the different relative base amount
     * @return a map that links the Base to the relative amount of it
     */
    public Map<Character, Integer> getAvgDifferentBasesTotal()
    {
        Map<Character, Integer> basesTotal = new HashMap<>();
        basesTotal.put( 'A', 0 );
        basesTotal.put( 'C', 0 );
        basesTotal.put( 'G', 0 );
        basesTotal.put( 'T', 0 );
        
        
        for( int i = 0; i < sequences.size(); i++ )
        {
            basesTotal.put( 'A', basesTotal.get('A') + sequences.get( i ).getNumberOfDifferentBasesTotal().get( 'A' ) );
            basesTotal.put( 'C', basesTotal.get('C') + sequences.get( i ).getNumberOfDifferentBasesTotal().get( 'C' ) );
            basesTotal.put( 'G', basesTotal.get('G') + sequences.get( i ).getNumberOfDifferentBasesTotal().get( 'G' ) );
            basesTotal.put( 'T', basesTotal.get('T') + sequences.get( i ).getNumberOfDifferentBasesTotal().get( 'T' ) );
            
        }
        
        basesTotal.put( 'A' , basesTotal.get( 'A' ) / sequences.size() );
        basesTotal.put( 'C' , basesTotal.get( 'C' ) / sequences.size() );
        basesTotal.put( 'G' , basesTotal.get( 'G' ) / sequences.size() );
        basesTotal.put( 'T' , basesTotal.get( 'T' ) / sequences.size() );
        
        
        return basesTotal;
    }
    
    /**
     * calculates the biggest ORF and returns it
     * @return the longest ORF
     */
    public OpenReadingFrame getBiggestORF()
    {
        OpenReadingFrame biggestORF = null;
        int length = 0;
        for( int i = 0; i < sequences.size(); i++ )
        {
            if( sequences.get( i ).getBiggestORF() != null )
            {
                OpenReadingFrame currentORF = sequences.get( i ).getBiggestORF();
                if( currentORF.getBases().length() > length )
                {
                    length = currentORF.getBases().length();
                    biggestORF = currentORF;
                }
            }
            
        }
        return biggestORF;
    }
    
    /**
     * getter for the Sequences
     * @return a List of the Sequences
     */
    public List<Sequence> getSequences()
    {
        return sequences;
    }
}
