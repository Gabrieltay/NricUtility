package dev.gabriel;

import java.util.Calendar;

public class Main {

    private static final char[] NRIC_LAST_ALPHABETS = new char[] {'J','Z','I','H','G','F','E','D','C','B','A'};
    private static final char[] FIN_LAST_ALPHABETS = new char[] {'X','W','U','T','R','Q','P','N','M','L','K'};

    private static char determineAlphabet(String nricFin) {
        char[] nricCharArray = nricFin.toUpperCase().toCharArray();
        int[] nricCheckSum = new int[7];

        nricCheckSum[0] = Integer.parseInt(String.valueOf(nricCharArray[1])) * 2;
        nricCheckSum[1] = Integer.parseInt(String.valueOf(nricCharArray[2])) * 7;
        nricCheckSum[2] = Integer.parseInt(String.valueOf(nricCharArray[3])) * 6;
        nricCheckSum[3] = Integer.parseInt(String.valueOf(nricCharArray[4])) * 5;
        nricCheckSum[4] = Integer.parseInt(String.valueOf(nricCharArray[5])) * 4;
        nricCheckSum[5] = Integer.parseInt(String.valueOf(nricCharArray[6])) * 3;
        nricCheckSum[6] = Integer.parseInt(String.valueOf(nricCharArray[7])) * 2;

        int sum = 0;
        for(int i=0; i<nricCheckSum.length; i++){
            sum += nricCheckSum[i];
        }

        if ( nricCharArray[0] == 'T' || nricCharArray[0] == 'G'){
            sum += 4;
        }

        char[] lastAlphabets;
        if ( nricCharArray[0] == 'S' || nricCharArray[0] == 'T') {
            lastAlphabets = NRIC_LAST_ALPHABETS;
        } else {
            lastAlphabets = FIN_LAST_ALPHABETS;
        }

        return lastAlphabets[sum % lastAlphabets.length];
    }

    private static boolean validate(String nricFin) {
        return nricFin.charAt(8) == determineAlphabet(nricFin);
    }

    private static String generate(int year, boolean isResidence) {
        char[] nricCharArray = new char[9];

        if (year < 2000 && isResidence) {
            nricCharArray[0] = 'S';
        } else if ( year >= 2000 && isResidence) {
            nricCharArray[0] = 'T';
        } else if ( year < 2000 ) {
            nricCharArray[0] = 'F';
        } else {
            nricCharArray[0] = 'G';
        }

        nricCharArray[1] = String.valueOf((year % 100)/10).toCharArray()[0];
        nricCharArray[2] = String.valueOf(year % 10).toCharArray()[0];
        nricCharArray[3] = String.valueOf(Math.random() * 10).toCharArray()[0];
        nricCharArray[4] = String.valueOf(Math.random() * 10).toCharArray()[0];
        nricCharArray[5] = String.valueOf(Math.random() * 10).toCharArray()[0];
        nricCharArray[6] = String.valueOf(Math.random() * 10).toCharArray()[0];
        nricCharArray[7] = String.valueOf(Math.random() * 10).toCharArray()[0];
        nricCharArray[8] = determineAlphabet(String.valueOf(nricCharArray));

        return String.valueOf(nricCharArray);
    }

    private static String generate(boolean isResidence) {
        int curYear = Calendar.getInstance().get(Calendar.YEAR);
        int year = (int) (Math.random() * ( curYear - 1968 ));
        return generate(year, isResidence);
    }

    private static String generate() {
        return generate(Math.random() < 0.5);
    }

    public static void main(String[] args) {
        String nric1 = generate();
        String nric2 = generate(true);
        String nric3 = generate(false);
        String nric4 = generate(1999, true);
        String nric5 = generate(2010, false);
        System.out.println("T1042182E" + "(" + validate("T1042182E") + ")");
        System.out.println("T1042182F" + "(" + validate("T1042182F") + ")");
        System.out.println(nric1 + "(" + validate(nric1) + ")");
        System.out.println(nric2 + "(" + validate(nric2) + ")");
        System.out.println(nric3 + "(" + validate(nric3) + ")");
        System.out.println(nric4 + "(" + validate(nric4) + ")");
        System.out.println(nric5 + "(" + validate(nric5) + ")");
    }
}
