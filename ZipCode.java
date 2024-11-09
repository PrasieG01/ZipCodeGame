public class Zipcode {
    private int zip;
    private String barcode;

    // Encoding weights and bars for each digit from 0 to 9
    private static final int[] WEIGHTS = {7, 4, 2, 1, 0};
    private static final String[] DIGIT_TO_BAR = {
        "||:::", // 0
        ":::||", // 1
        "::|:|", // 2
        "::||:", // 3
        ":|::|", // 4
        ":|:|:", // 5
        ":||::", // 6
        "|:::|", // 7
        "|::|:", // 8
        "|:|::"  // 9
    };

    // Constructor for encoding ZIP code to barcode
    public Zipcode(int zip) {
        this.zip = zip;
        this.barcode = generateBarcode();
    }

    // Constructor for decoding barcode to ZIP code
    public Zipcode(String barcode) {
        this.barcode = barcode;
        this.zip = decodeBarcode();
    }

    // Method to generate the barcode from ZIP code
    private String generateBarcode() {
        StringBuilder barcodeBuilder = new StringBuilder("|"); // Start frame bar
        int sum = 0;

        // Process each digit of the ZIP code
        int zipTemp = zip;
        for (int i = 0; i < 5; i++) {
            int digit = zipTemp % 10;
            zipTemp /= 10;
            sum += digit;
            barcodeBuilder.insert(1, DIGIT_TO_BAR[digit]); // Insert at the beginning after frame bar
        }

        // Calculate the check digit to make the sum a multiple of 10
        int checkDigit = (10 - (sum % 10)) % 10;
        barcodeBuilder.append(DIGIT_TO_BAR[checkDigit]); // Add check digit encoding
        barcodeBuilder.append("|"); // End frame bar

        return barcodeBuilder.toString();
    }

    // Method to decode the barcode back into a ZIP code
    private int decodeBarcode() {
        if (barcode.length() != 32 || barcode.charAt(0) != '|' || barcode.charAt(barcode.length() - 1) != '|') {
            throw new IllegalArgumentException("Invalid barcode format");
        }

        int sum = 0;
        int zipResult = 0;

        // Process each set of 5 bars (excluding frame bars) and decode each digit
        for (int i = 1; i <= 25; i += 5) {
            String digitBars = barcode.substring(i, i + 5);
            int digit = getDigitFromBars(digitBars);
            if (digit == -1) {
                throw new IllegalArgumentException("Invalid bar encoding");
            }

            if (i <= 20) {
                zipResult = zipResult * 10 + digit;
                sum += digit;
            } else {
                // Check digit
                int checkDigit = digit;
                if ((sum + checkDigit) % 10 != 0) {
                    throw new IllegalArgumentException("Checksum error in barcode");
                }
            }
        }

        return zipResult;
    }

    // Helper method to get a digit from the 5-bar encoding
    private int getDigitFromBars(String bars) {
        for (int i = 0; i < DIGIT_TO_BAR.length; i++) {
            if (DIGIT_TO_BAR[i].equals(bars)) {
                return i;
            }
        }
        return -1; // Invalid encoding
    }

    // Method to return the generated barcode
    public String getBarcode() {
        return barcode;
    }

    // Method to return the decoded ZIP code
    public int getZIPcode() {
        return zip;
    }
}
