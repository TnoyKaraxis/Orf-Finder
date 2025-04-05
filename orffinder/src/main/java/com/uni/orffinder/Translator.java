package com.uni.orffinder;

import java.util.HashMap;
import java.util.Map;

/**
 * a static class that has dictionarys for the names of the bases and amino acids and one for the codons to the amino acids
 * @author Philipp
 */
public class Translator {
    /**
     * generates the dictionary for the bases
     * @return a map that links the character of the base to the corresponding name
     */
    public static final Map<Character, String> baseDictionary()
    {
        Map<Character, String> basesDictionary = new HashMap<>();
        basesDictionary.put( 'A', "Adenine" );
        basesDictionary.put( 'C', "Cytosine" );
        basesDictionary.put( 'T', "Thymine" );
        basesDictionary.put( 'G', "Guanine" );
        return basesDictionary;
    }

    /**
     * generates the dictionary for the amino acids
     * @return a map that links the character of the amino acid to the corresponding name
     */
    public static final Map<Character, String> aminoAcidDictionary()
    {
        Map<Character, String> aminoAcidDictionary = new HashMap<>();
        aminoAcidDictionary.put( 'A', "Alanine" );
        aminoAcidDictionary.put( 'R', "Arginine" );
        aminoAcidDictionary.put( 'N', "Asparagine" );
        aminoAcidDictionary.put( 'D', "Aspartate" );
        aminoAcidDictionary.put( 'C', "Cysteine" );
        aminoAcidDictionary.put( 'Q', "Glutamine" );
        aminoAcidDictionary.put( 'E', "Glutamate" );
        aminoAcidDictionary.put( 'G', "Glycine" );
        aminoAcidDictionary.put( 'H', "Histidine" );
        aminoAcidDictionary.put( 'I', "Isoleucine" );
        aminoAcidDictionary.put( 'L', "Leucine" );
        aminoAcidDictionary.put( 'K', "Lysine" );
        aminoAcidDictionary.put( 'M', "Methionine" );
        aminoAcidDictionary.put( 'F', "Phenylalanine" );
        aminoAcidDictionary.put( 'P', "Proline" );
        aminoAcidDictionary.put( 'S', "Serine" );
        aminoAcidDictionary.put( 'T', "Threonine" );
        aminoAcidDictionary.put( 'W', "Tryptophan" );
        aminoAcidDictionary.put( 'Y', "Tyrosine" );
        aminoAcidDictionary.put( 'V', "Valine" );
        aminoAcidDictionary.put( 'U', "Selenocysteine" );
        aminoAcidDictionary.put( 'O', "Pyrrolysine" );
        return aminoAcidDictionary;
    }

    /**
     * generates the dictionary for the codons to the amino acids
     * @return a map that links the codon to the corresponding character of the amino acid
     */
    public static final Map<String, Character> CodonDictionary()
    {
        Map<String, Character> decode = new HashMap<>();
        decode.put( "AAA", 'K');
        decode.put( "AAC", 'N');
        decode.put( "AAG", 'K');
        decode.put( "AAT", 'N');
        decode.put( "ACA", 'T');
        decode.put( "ACC", 'T');
        decode.put( "ACG", 'T');
        decode.put( "ACT", 'T');
        decode.put( "ATA", 'I');
        decode.put( "ATC", 'I');
        decode.put( "ATG", 'M');
        decode.put( "ATT", 'I');
        decode.put( "AGA", 'R');
        decode.put( "AGC", 'S');
        decode.put( "AGG", 'R');
        decode.put( "AGT", 'S');

        decode.put( "CAA", 'Q');
        decode.put( "CAC", 'H');
        decode.put( "CAG", 'Q');
        decode.put( "CAT", 'H');
        decode.put( "CCA", 'P');
        decode.put( "CCC", 'P');
        decode.put( "CCG", 'P');
        decode.put( "CCT", 'P');
        decode.put( "CTA", 'L');
        decode.put( "CTC", 'L');
        decode.put( "CTG", 'L');
        decode.put( "CTT", 'L');
        decode.put( "CGA", 'R');
        decode.put( "CGC", 'R');
        decode.put( "CGG", 'R');
        decode.put( "CGT", 'R');

        decode.put( "GAA", 'E');
        decode.put( "GAC", 'D');
        decode.put( "GAG", 'E');
        decode.put( "GAT", 'D');
        decode.put( "GCA", 'A');
        decode.put( "GCC", 'A');
        decode.put( "GCG", 'A');
        decode.put( "GCT", 'A');
        decode.put( "GTA", 'V');
        decode.put( "GTC", 'V');
        decode.put( "GTG", 'V');
        decode.put( "GTT", 'V');
        decode.put( "GGA", 'G');
        decode.put( "GGC", 'G');
        decode.put( "GGG", 'G');
        decode.put( "GGT", 'G');

        decode.put( "TAC", 'Y');
        decode.put( "TAT", 'Y');
        decode.put( "TCA", 'S');
        decode.put( "TCC", 'S');
        decode.put( "TCG", 'S');
        decode.put( "TCT", 'S');
        decode.put( "TTA", 'L');
        decode.put( "TTC", 'F');
        decode.put( "TTG", 'L');
        decode.put( "TTT", 'F');
        decode.put( "TGC", 'C');
        decode.put( "TGG", 'W');
        decode.put( "TGT", 'C');
        return decode;
    }
}
