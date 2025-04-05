package com.uni.orffinder;

import java.util.HashMap;
import java.util.Map;

/**
 * a helper class of a specific amino acid that holds the letter of it and the total and relative amount of it
 * @author Philipp
 */
public class SpecificAminoAcid {
    /**
     * the letter of the current acid
     */
    private final char acid;

    /**
     * links the Codon to the amount of it in this acid
     */
    private final Map<String, Integer> amountOfCodons;

    /**
     * links the codon to the relative amount of it in this acid
     */
    private Map<String, Double> codonRate;

    /**
     * creates a amino acid with the letter of it and the amount of each codon
     * @param acid the letter of the acid
     * @param amountOfCodons map of the codons with the amount of them
     */
    public SpecificAminoAcid( char acid, Map<String, Integer> amountOfCodons )
    {
        this.acid = acid;
        this.amountOfCodons = amountOfCodons;
        calculateCodonRate();
    }

    /**
     * calculates the relative amount of each codon in this acid
     */
    private void calculateCodonRate()
    {
        codonRate = new HashMap<>();
        int numberOfCodons = 0;
        for( String codon : amountOfCodons.keySet() )
        {
            numberOfCodons = numberOfCodons + amountOfCodons.get( codon );
        }
        for ( String codon : amountOfCodons.keySet() )
        {
            codonRate.put( codon, (double) amountOfCodons.get( codon ) /numberOfCodons );
        }
    }

    /**
     * getter for the letter of the acid
     * @return the letter of the acid
     */
    public char getAcid()
    {
        return acid;
    }

    /**
     * getter for the total codon amounts
     * @return a map that links the codon to the total amount of it
     */
    public Map getAmountOfCodons()
    {
        return amountOfCodons;
    }

    /**
     * getter for the relative codon amount
     * @return a Map that links the codon to the relative amount of it
     */
    public Map getCodonRate()
    {
        return codonRate;
    }
}
