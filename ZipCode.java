public class ZipCode
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
public ZipCode(int zipCode)
{
    this.zipCode = zipCode;
    this.barCode = convertZtoB(zipCode);
}

 // Constructor for barcode to zipcode conversion
 public ZipCode(String barCode)
 {
    this.barCode = barCode;
    this.zipCode = Integer.valueOf((convertBtoZ(barCode)));
 }

    // Convert ZIP code to bar code
    public String convertZtoB(int zipCode)
    {
       String barCodeS = "|"; // Start with the left frame bar

//Format the ZIP code 
String zipCodeS = String.format("%05d", zipCode);
for(char c : zipCodeS.toCharArray())
{
    int digit = Character.getNumericValue(c); //convert the char to an int
    barCodeS += patterns[digit]; //add corresponding encoding pattern for the digit

}
barCodeS += "|"; //Closing frame bar
return barCodeS;
    }

    // Convert barcode to ZIP code
    public String convertBtoZ(String barCode)
    {
        StringBuilder zipCodeS = new StringBuilder();

        for(int i = 1; i <barCode.length() - 1; i += 5)
        {
            String barSeg = barCode.substring(i, i + 5);
            int digit = -1;
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
     public String getBarCode()
     {
        return barCode;
     }

     // Method to get the zipcode
     public int getZipCode()
     {
        return zipCode;
     }

}
