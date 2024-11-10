public class Zipcode
{

private int zipCode;
private String barCode;

// Arrays that represent bar encoded patterns for each digit from 0 to 9
private static final String[] patterns = {
        "||:::",    // 0
        ":::||",   // 1
        "::|:|",  // 2
        "::||:", // 3
        ":|::|", // 4
        ":|:|:", // 5
        ":||::", // 6
        "|:::|", // 7
        "|::|:", // 8
        "|:|::"  // 9
};

// Constructor for zipcode to barcode conversion
public Zipcode(int zipCode)
{
    this.zipCode = zipCode;
    this.barCode = convertZtoB(zipCode);
}

 // Constructor for barcode to zipcode conversion
 public Zipcode(String barCode)
 {
    this.barCode = barCode;
    this.zipCode = Integer.valueOf((convertBtoZ(barCode)));
 }

    // Convert ZIP code to bar code
    public String convertZtoB(int zipCode)
    {
        // Start with left frame bar
        StringBuilder barCodeS = new StringBuilder("|");

        // Convert the ZIP code to a 5-digit string 
        String zipCodeS = Integer.toString(zipCode); //convert zipcode to a string
        while(zipCodeS.length() < 5)
        {
            zipCodeS += "0" + zipCodeS;
        }

        // Calculate the check digit
        int sum = 0;
        for(int i = 0; i <zipCodeS.length(); i++)
        {
            sum += zipCodeS.charAt(i) - '0'; // Subtract '0' to get the numeric value
        }
            int checkDigit = (10 - (sum % 10)) % 10;

            // Append the patterns for each digit in the ZIP code
            for(int i = 0; i < zipCodeS.length(); i++)
            {
                int digit = zipCodeS.charAt(i) - '0'; //convert char to its integer value
                barCodeS.append(patterns[digit]); //append the corresponding bar pattern
            }

            // Append the pattern for the check digit
            barCodeS.append(patterns[checkDigit]);

            // End with the right frame bar
            barCodeS.append("|");

            return barCodeS.toString();
    }

    // Convert barcode to ZIP code
    public String convertBtoZ(String barCode)
    {
        StringBuilder zipCodeS = new StringBuilder();

        for(int i = 1; i <barCode.length() - 6; i += 5)
        {
            String barSeg = barCode.substring(i, i + 5);
            int digit = -1;

            // Match the bar segment with the pattern for each digit
            for(int j = 0; j < patterns.length; j++)
            {
                if(patterns[j].equals(barSeg))
                {
                    digit = j;
                    break;
                }
            }
            zipCodeS.append(digit); //append the matched digit to the result
            
            }
            return zipCodeS.toString(); //return the zipcode as a String
        }

    // Method to get the barcode
     public String getBarcode()
     {
        return barCode;
     }

     // Method to get the zipcode
     public int getZIPcode()
     {
        return zipCode;
     }

}

