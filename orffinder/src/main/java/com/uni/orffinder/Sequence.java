/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uni.orffinder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Philipp
 */
public class Sequence {
    /**
     * The Bases of the current Sequence
     */
    private String bases;
    
    private boolean reverse;
    
    /**
     * 
     */
    private String reverseBases;
    
    /**
     * A Dictionary that links all letters of the aminoAcids with the full name
     */
    public static Map <Character, String> aminoAcidDictionary;
    /**
     * A Dictionary that links all letters of the bases with the full name
     */
    private static Map <Character, String> basesDictionary;
    /**
     * A List with all ORFS of this sequence
     */
    private List<OpenReadingFrame> orfs;

    /**
     * Initiallizes the Sequence with the given number of random Bases
     * @param numberOfBases the number of bases to generate
     * @param reverse
     * @throws java.io.IOException
     */
    public Sequence( int numberOfBases, boolean reverse ) throws IOException
    {
        init();
        this.reverse = reverse;
        Random rand = new Random();
        for( int i = 0; i < numberOfBases; i++ )
        {
            int temp = rand.nextInt(4);
            switch (temp) {
                case 0:
                    bases = bases + 'A';
                    break;
                case 1:
                    bases = bases + 'C';
                    break;
                case 2:
                    bases = bases + 'G';
                    break;
                case 3:
                    bases = bases + 'T';
                    break;
                default:
                    throw new AssertionError();
            }
        }
        if( !bases.matches("[actgACTG]+") )
        {
            throw new IOException("Bases contain a letter that isn't a base");
        }
        else
        {
            bases = bases.toUpperCase();
        }
    }
    
    

    /**
     * Initiallizes the Sequence with the given number of random Bases, and the given weights for them
     * @param numberOfBases the number of bases to generate
     * @param weightsACGT an array of the weights of the bases in order actg
     * @param reverse a boolean if the reverse String should be generated and looked at
     * @throws java.io.IOException
     */
    public Sequence( int numberOfBases, double[] weightsACGT, boolean reverse ) throws IOException
    {
        init();
        this.reverse = reverse;
        if( weightsACGT.length != 4 )
        {
            throw new IOException();
        }
        double totalWeight = 0.0;
        for( int i = 0; i < weightsACGT.length; i++ )
        {
            totalWeight = totalWeight + weightsACGT[i];
        }
        for( int i = 0; i < numberOfBases; i++ )
        {
            int idx = 0;
            for (double r = Math.random() * totalWeight; idx < 4 - 1; ++idx)
            {
                r -= weightsACGT[idx];
                if (r <= 0.0) break;
            }
            switch (idx ) {
                case 0:
                    bases = bases + 'A';
                    break;
                case 1:
                    bases = bases + 'C';
                    break;
                case 2:
                    bases = bases + 'G';
                    break;
                case 3:
                    bases = bases + 'T';
                    break;
                default:
                    throw new AssertionError();
            }
        }
        
        

    }

    /**
     * Initiallizes the Sequence with a .txt-File at the given path
     * @param path the path of the txt-File with bases
     * @param reverse a boolean if the reverse String should be generated and looked at
     * @throws java.io.IOException if wrong letters are in the file or the file isnt found
     */
    public Sequence( String path, boolean reverse ) throws IOException
    {
        init();
        this.reverse = reverse;
        boolean worked = loadBases(path);
        if( !worked )
        {
            throw new IOException( "File couldnt be found" );
        }
        else
        {
            if( !bases.matches("[acgtACGT]+") || bases.equals( "" ) )
            {
                throw new IOException("File contains a letter that isn't a base or is empty");
            }
            else
            {
                bases = bases.toUpperCase();
            }
        }
        
    }
    
    /**
     * Generate a Sequence with given bases
     * @param bases a String of all the Bases of the sequence
     * @param numberOfBases the number of bases
     * @param reverse a boolean if the ORFs in the reverse String should be looked at
     * @throws IOException wrong input
     */
    public Sequence( String bases, int numberOfBases, boolean reverse ) throws IOException
    {
        init();
        this.reverse = reverse;
        if( numberOfBases != bases.length() )
        {
            throw new IOException( "bases length dosent match the actual length of them" );
        }
        else if( !bases.matches("[acgtACGT]+") || bases.equals( "" ) )
        {
            throw new IOException("Bases contain a letter that isn't a base or is empty");
        }
        else
        {
            this.bases = bases.toUpperCase();
        }
    }

    /**
     * Initiallizes the class-variables, only called by constructors
     */
    private void init()
    {
        bases = "";

        basesDictionary = Translator.baseDictionary();
        
        aminoAcidDictionary = Translator.aminoAcidDictionary();
        

    }

    /**
     * Writes the bases of the current sequence to a given txt-file
     * @param path the path of the txt-file to write in
     * @return a boolean if the save worked or not
     */
    public boolean saveBases( String path )
    {
        try
        {
            try (BufferedWriter writer = new BufferedWriter( new FileWriter( path ))) {
                writer.write( bases );
            }
            return true;
        }
        catch( IOException ex )
        {
            return false;
        }
    }

    /**
     * loads bases from a given txt-file, called by one constructor
     * @param path the path of the txt-file to load
     * @return a boolean if the loading was successfull
     */
    private boolean loadBases( String path )
    {
        try
        {
            bases = new String(Files.readAllBytes( Paths.get( path ) ) );
            return true;
        }
        catch(IOException ex)
        {
            return false;
        }
    }

    /**
     * iterates through the bases to find the open reading frames and to generate a list of them
     * @return a boolean if the generating was successfull
     * @throws java.lang.Exception
     */
    public boolean generateOpenReadingFrames() throws Exception
    {
        orfs = new ArrayList<>();
        boolean startFound = false;
        int i = 0;

        if( bases.length() < 3 )
        {
            return false;
        }

        while( i < bases.length()  - 5 )
        {
            if( !startFound )
            {
                String currentStart = bases.substring(i, i + 3);
                if( currentStart.equals( "ATG" ) )
                {
                    startFound = true;
                }
                else
                {
                    i++;
                }
            }
            else
            {
                int j = i;
                boolean endFound = false;
                while( !endFound && j < bases.length() - 2 )
                {
                    String currentEnd = bases.substring( j, j + 3 );
                    if( currentEnd.equals( "TGA" ) || currentEnd.equals( "TAG" ) || currentEnd.equals( "TAA" ) )
                    {
                        OpenReadingFrame orf = new OpenReadingFrame( bases.substring( i, j + 3 ) , i, false);
                        orfs.add( orf );
                        endFound = true;
                        startFound = false;
                        i++;
                    }
                    else
                    {
                        j += 3;
                    }
                    if( j > bases.length() - 3 )
                    {
                        i++;
                        startFound = false;
                    }
                }
                
            }
        }
        if( reverse )
        {
            generateReverseORFs();
        }
        return true;
    }

    /**
     * uses the dictionary to translate the letter of a base to the full name
     * @param base the letter of the base to translate
     * @return the full name of the base
     * @throws java.io.IOException
     */
    public static String translateBase( char base ) throws IOException
    {
       return basesDictionary.get( base );
    }

    /**
     * uses the dictionary to translate the letter of an amino acid to the full name
     * @param acid the letter of the amino acid to translate
     * @return the full name of the amino acid
     * @throws java.io.IOException
     */
    public static String translateAminoAcid( char acid ) throws IOException
    {
        return aminoAcidDictionary.get( acid );
    }

    /**
     * returns the ORF with the biggest amount of bases in it
     * @return the ORF with the biggest amount of bases in it
     */
    public OpenReadingFrame getBiggestORF()
    {
        int biggest = 0;
        int pos = 0;
        if( orfs.isEmpty() )
        {
            return null;
        }
        for( int i = 0; i < orfs.size(); i++ )
        {
            if( orfs.get(i).getBases().length() > biggest )
            {
                biggest = orfs.get( i ).getBases().length();
                pos = i;
            }
        }
        return orfs.get( pos );
    }    
    
    /**
     * returns the position of the orf with the biggest amount of bases in it
     * @return the poition of the orf with the biggest amount of bases in it
     */
    public Integer getBiggestORFPosition()
    {
        int biggest = 0;
        int pos = 0;
        for( int i = 0; i < orfs.size(); i++ )
        {
            if( orfs.get(i).getBases().length() > biggest )
            {
                biggest = orfs.get( i ).getBases().length();
                pos = i;
            }
        }
        return pos;
    }

    /**
     * generates the number of the different bases in the sequence
     * @return a map that maps the letter of the base to the number of times that base is in the sequence
     */
    public Map<Character, Integer> getNumberOfDifferentBasesTotal()
    {
        int numberA = 0;
        int numberC = 0;
        int numberG = 0;
        int numberT = 0;


        for( int i = 0; i < bases.length(); i++ )
        {
            switch ( bases.charAt( i ) ) {
                case 'A':
                    numberA++;
                    break;
                case 'C':
                    numberC++;
                    break;
                case 'G':
                    numberG++;
                    break;
                case 'T':
                    numberT++;
                    break;
            }
        }
        Map<Character, Integer> result = new HashMap<>();
        result.put( 'A', numberA );
        result.put( 'C', numberC );
        result.put( 'G', numberG );
        result.put( 'T', numberT );
        
        return result;
    }
    

    /**
     * generates the relative number of the different bases in the sequence
     * @return a map that maps the letter of the base to the relative number of times that base is in the sequence
     */
    public Map<Character, Double> getDifferentBasesRelative()
    {
        Map<Character, Integer> total = getNumberOfDifferentBasesTotal();
        Map<Character, Double> result = new HashMap<>();
        for( char currentBase : total.keySet() )
        {
            result.put( currentBase, (double) total.get(currentBase) / bases.length() );
        }
        return result;
    }
    
    /**
     * generates the reverse Base String
     */
    private void generateReverseBases()
    {
        reverseBases = "";
        for( int i = bases.length() - 1; i >= 0; i-- )
        {
            switch( bases.charAt( i ) )
            {
                case 'A':
                    reverseBases = reverseBases + 'T';
                    break;
                case 'C':
                    reverseBases = reverseBases + 'G';
                    break;
                case 'T':
                    reverseBases = reverseBases + 'A';
                    break;
                case 'G':
                    reverseBases = reverseBases + 'C';
                    break;
            }
                    
        }
    }
    
    /**
     * 
     * @return
     * @throws IOException 
     */
    public boolean generateReverseORFs() throws IOException
    {
        generateReverseBases();
        if( orfs == null || orfs.isEmpty() )
        {
            return false;
        }
        boolean startFound = false;
        int i = 0;

        if( reverseBases.length() < 3 )
        {
            return false;
        }

        while( i < reverseBases.length()  - 5 )
        {
            if( !startFound )
            {
                String currentStart = reverseBases.substring(i, i + 3);
                if( currentStart.equals( "ATG" ) )
                {
                    startFound = true;
                }
                else
                {
                    i++;
                }
            }
            else
            {
                int j = i;
                boolean endFound = false;
                while( !endFound && j < reverseBases.length() - 2 )
                {
                    String currentEnd = reverseBases.substring( j, j + 3 );
                    if( currentEnd.equals( "TGA" ) || currentEnd.equals( "TAG" ) || currentEnd.equals( "TAA" ) )
                    {
                        OpenReadingFrame orf = new OpenReadingFrame( reverseBases.substring( i, j + 3 ), i, true );
                        orfs.add( orf );
                        endFound = true;
                        startFound = false;
                        i++;
                    }
                    else
                    {
                        j += 3;
                    }
                    if( j > reverseBases.length() - 3 )
                    {
                        i++;
                        startFound = false;
                    }
                }
                
            }
        }
        return true;
    }
    
    /**
     * Getter for the bases
     * @return the bases of the sequence as a String
     */
    public String getBases()
    {
        return bases;
    }
    
    public String getReverseBases()
    {
        return reverseBases;
    }

    public List<OpenReadingFrame> getORFs()
    {
        return orfs;
    }
}
