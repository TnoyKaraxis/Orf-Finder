/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uni.orffinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the orf holds the bases of one frame from start to stop codon
 * @author Philipp
 */
public class OpenReadingFrame {
    /**
     * the bases of the orf
     */
    private final String bases;

    /**
     * the amino acids of the orf
     */
    private String aminoAcids;

    /**
     * a Map that links the codons to the coresponding amino acid
     */
    private static Map <String, Character> codonDictionary;

    /**
     * a Map that links the letter of the amino acid to the full name of it
     */
    private static Map <Character, String> aminoAcidDictionary;
    
    /**
     * position of the first base of the ORF
     */
    private final int firstBasePosition;
    
    /**
     * a boolean signalling if the ORF is on the reverse String or not
     */
    private final boolean isOnReverse;

    /**
     * constructor that creates the orf with a given String of bases, calls generateAminoAcids
     * @param bases the bases of the orf that starts with a start-codon and ends with a stop-codon
     * @param firstBasePosition
     * @param reverse
     * @throws java.io.IOException when the ORF dose'nt with start or end wit stop codon
     */
    public OpenReadingFrame( String bases, int firstBasePosition, boolean reverse ) throws IOException
    {
        this.bases = bases;
        this.firstBasePosition = firstBasePosition;
        isOnReverse = reverse;
        if( !bases.substring(0, 3).equals("ATG") || !(bases.substring(bases.length()-3, bases.length() ).equals("TGA")
        || bases.substring(bases.length()-3, bases.length() ).equals("TAA") 
        || bases.substring(bases.length()-3, bases.length() ).equals("TAG") )
        || bases.length() == 0
        || bases.length() % 3 != 0 )
        {
            throw new IOException("ORF dosen't start with start codon or dosen't end with stop-Codon");
        }
        codonDictionary = Translator.CodonDictionary();
        generateAminoAcids();
        aminoAcidDictionary = Translator.aminoAcidDictionary();
    }

    /**
     * generates the amino-acids from the bases with the dictionary
     * @return a boolean if the generation worked or not
     */
    private boolean generateAminoAcids()
    {
        aminoAcids = "";
        for( int i = 0; i < bases.length() - 3; i+=3)
        {
            String current = bases.substring( i, i + 3 );
            char currentAcid = translateCodon(current);
            aminoAcids = aminoAcids + currentAcid;
        }
        return true;
    }
    
    /**
     * generates a Map that links every base to the relative amount in the ORF
     * @return a Map that links every base to the relative amount in the ORF
     */
    public Map<Character, Double> generateRelativeBases()
    {
        Map<Character, Double> relativeBases = new HashMap<>();
        relativeBases.put( 'A', 0.0 );
        relativeBases.put( 'C', 0.0 );
        relativeBases.put( 'G', 0.0 );
        relativeBases.put( 'T', 0.0 );
        for( int i = 0; i < bases.length(); i++ )
        {
            switch( bases.charAt(i) )
            {
                case 'A':
                    relativeBases.put( 'A', relativeBases.get( 'A' ) + 1 );
                    break;
                case 'C':
                    relativeBases.put( 'C', relativeBases.get( 'C' ) + 1 );
                    break;
                case 'G':
                    relativeBases.put( 'G', relativeBases.get( 'G' ) + 1 );
                    break;
                case 'T':
                    relativeBases.put( 'T', relativeBases.get( 'T' ) + 1 );
                    break;
            }
            
            
            
            
        }
        relativeBases.put( 'A', ( relativeBases.get( 'A' ) / bases.length() ) );
        relativeBases.put( 'C', ( relativeBases.get( 'C' ) / bases.length() ) );
        relativeBases.put( 'G', ( relativeBases.get( 'G' ) / bases.length() ) );
        relativeBases.put( 'T', ( relativeBases.get( 'T' ) / bases.length() ) );
        return relativeBases;
    }

    /**
     * generates a Map that links the codons with the number of occurrences of a specific acid
     * @param acid the acid to generate the codon-numbers from
     * @return a Map that links the codons to the number of their occurrence
     */
    public Map<String, Integer> getTotalNumberOfCodonsWithSpecificAcid( char acid )
    {
        if( aminoAcids.equals("") )
        {
            return null;
        }
        Map<String, Integer> codons = new HashMap<>();
        for(int i = 0; i < aminoAcids.length(); i++ )
        {
            if( aminoAcids.charAt( i ) == acid )
            {
                String currentCodon = bases.substring(i*3, i*3 + 3);
                if( codons.containsKey( currentCodon ) )
                {
                    codons.replace( currentCodon, codons.get( currentCodon) + 1 );
                }
                else
                {
                    codons.put( currentCodon, 1 );
                }
            }
        }
        return codons;
    }

    /**
     * generates a List of SpecificAminoAcids to hold the total and relative number of each codon for each amino acid
     * @return a list of all aminoAcids that hold the number of each codon
     */
    public List<SpecificAminoAcid> getAllTotalAndRelativeCodonsForAcids()
    {
        List<SpecificAminoAcid> codonRates = new ArrayList<>();
        for (char currentAcid : aminoAcidDictionary.keySet() ) {
            codonRates.add( new SpecificAminoAcid(currentAcid, getTotalNumberOfCodonsWithSpecificAcid(currentAcid) ) );
        }
        return codonRates;
    }



    /**
     * gets the amino acid for a given codon
     * @param codon the codon the amino acid should be translated from
     * @return the letter of the amino acid of the codon
     */
    private static Character translateCodon( String codon )
    {
        return codonDictionary.get( codon );
    }

    /**
     * generates the number of each acid in this orf
     * @return a map that links the letter of each acid to the corresponding number of it in this orf
     */
    public Map<Character, Integer> getTotalNumberOfDifferentAminoAcids()
    {
        Map<Character, Integer> result = new HashMap<>();
        for( char current : aminoAcidDictionary.keySet() )
        {
            result.put( current, 0 );
        }
        for( int i = 0; i < aminoAcids.length(); i++ )
        {
            char current = aminoAcids.charAt(i);
            result.replace( current , result.get( current ) + 1 );
        }
        return result;
    }

    /**
     * generates the relative number of each amino acid
     * @return a map that links each amino acid to the relative number of the acid in the orf
     */
    public Map<Character, Double> getRelativeNumberOfDifferentAminoAcids()
    {
        Map<Character, Integer> total = getTotalNumberOfDifferentAminoAcids();
        Map<Character, Double> result = new HashMap<>();
        for( char currentAminoAcid : total.keySet() )
        {
            result.put( currentAminoAcid, (double) total.get(currentAminoAcid) / bases.length() );
        }
        return result;
    }

    /**
     * getter for the bases of this orf
     * @return a String of the bases letters
     */
    public String getBases()
    {
        return bases;
    }

    /**
     * getter for the amino acids of this orf
     * @return a String of the amino Acid letters
     */
    public String getAminoAcids()
    {
        return aminoAcids;
    }
    
    /**
     * getter for the position of the orf
     * @return the integer with the position
     */
    public Integer getPosition()
    {
        return firstBasePosition;
    }
    
    /**
     * getter for the boolean if the ORF is on the reverse String
     * @return the boolean
     */
    public boolean isOnReverse()
    {
        return isOnReverse;
    }
}
